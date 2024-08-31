package com.paytonkawa.commande_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;

import com.paytonkawa.commande_service.dto.CommandDto;
import com.paytonkawa.commande_service.dto.UpdateProductStockDto;
import com.paytonkawa.commande_service.entity.Command;
import com.paytonkawa.commande_service.entity.Product;
import com.paytonkawa.commande_service.repo.CommandRepo;
import com.paytonkawa.commande_service.rest_client.ProductFeignClient;

@Service
public class CommandServices {


	private CommandRepo commandRepo;
	private ProductFeignClient productRestClient;
	@Value("${kafka.topic}")
	private String messageTopic;
	@Autowired
	private KafkaTemplate<?, ?> KafkaTemplate;
	
	public CommandServices(CommandRepo commandRepo,
			ProductFeignClient productRestClient
			) {
		this.commandRepo = commandRepo;
		this.productRestClient = productRestClient;
	}

	public ResponseEntity<List<Command>> getAllCommands() {
		List<Command> commands = this.commandRepo.findAll();
		return ResponseEntity.ok(commands);
	}
	
    public ResponseEntity<Command> addCommand(int customerId,CommandDto commandDto){
    	Command commandToSave = new Command();
    	commandToSave.setCustomerId(customerId);
		Command command = this.commandRepo.save(commandToSave);
		
		return addProductToCart(command.getId(),commandDto);
	}
	
	public ResponseEntity<Command> addProductToCart(int commandId,CommandDto commandDto){
		System.out.println("adding product to cart ");
		int productId = commandDto.getProductId();
		int quantity = commandDto.getQuantity();
		Product product = getProductById(productId);
		if(product!=null && product.getStock()>=quantity) {
			try {
				System.out.println("product found and available stock  ");
				Command command = this.commandRepo.findById(commandId).get();
				if(command.isValidated()==false) {
					product.setQuantity(quantity);
					command.getProducts().add(product);
					this.commandRepo.save(command);
					//update the product database by comunicating with product-service with kafka(to add later)
					return ResponseEntity.ok(command);
				}
				
			} catch (Exception e) {
				System.out.println("somethinig went wrong =>"+e.getMessage());
			return ResponseEntity.badRequest().build();
			}
		}
		System.out.println("somethinig went wrong ");
		return ResponseEntity.badRequest().build();
	}
	
	/*public void addProductToCommand(Command command,Product product) {
		if(command.getProducts().contains(product)) {
			Product productToUpdate = command.getProducts().get(command.getProducts().indexOf(product));
			productToUpdate.setQuantity(product.getQuantity()+productToUpdate.getQuantity());
			productToUpdate.setStock(productToUpdate.getStock()+product.getQuantity());
		}else {
			command.getProducts().add(product);
		}
	}*/
	

	public ResponseEntity<Command> getCommandById(int commandId){
		Command command = this.commandRepo.findById(commandId).get();
		return ResponseEntity.ok(command);
	}
	public ResponseEntity<List<Product>>getCommandProducts(int commandId){
		Command command = this.commandRepo.findById(commandId).get();
		List<Product> products = command.getProducts();
		return ResponseEntity.ok(products);
	}
	public ResponseEntity<Map<String,String>> validateCommand(int commandId){
		List<String> availabilityAndStock=new ArrayList<>();
		Command command = this.commandRepo.findById(commandId).get();
		if(!command.isValidated()) {
			for(Product p : command.getProducts()) {
				Product product = this.getProductById(p.getId());
				if(product==null) availabilityAndStock.add("product with name=>" +p.getName()+ " not available no more ");
				else {
					availabilityAndStock.add( product.getStock()<p.getQuantity()==true?
							"product with (id,name)=>("+product.getId()+","+product.getName()+")not available in wanted quantity,max available"+product.getStock()
							:"product with (id,name) ("+product.getId()+ ","+product.getName()+")purchased successfully");
				}	
			}
			if(availabilityAndStock.size()>=1) {
				command.setValidated(true);
				
				command.getProducts().stream().forEach(x->{
					sendUpdateStockMesssage(new UpdateProductStockDto(x.getId(), x.getQuantity()));
				});
				this.commandRepo.save(command);
				return ResponseEntity.ok(Map.of("message",availabilityAndStock+""));
			}else return ResponseEntity.badRequest().body(Map.of("command no validé",""+availabilityAndStock));
		}
		
		return ResponseEntity.badRequest().body(Map.of("error :"," can t validate a command that is already valide"));

		}

	
	public ResponseEntity<String> deleteCommand(int commandId){
		try {
			 this.commandRepo.deleteById(commandId);
			 return ResponseEntity.ok("command with id=>" +commandId+" deleted successfully");
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("error deleting the product");
		}
	}
	public Product getProductById(int productId){
		ResponseEntity<Product> productResponse = this.productRestClient.getProductById(productId);
		System.out.println(" product  found =>"+productResponse);
		if(productResponse.getStatusCode().is2xxSuccessful()){
			Product product = productResponse.getBody();
			return product;
		}
		return null;
	}
	
	public void sendUpdateStockMesssage(UpdateProductStockDto updateStockMessage) {
		try {
			System.out.println("sending update message productId=" +updateStockMessage.getProductId()+ " quantity=" +updateStockMessage.getQuantity());
			Message<UpdateProductStockDto> update = MessageBuilder
					.withPayload(updateStockMessage)
					.setHeader(KafkaHeaders.TOPIC, "update_stock")
					.build();

			KafkaTemplate.send(update);
		} catch (Exception e) {
			System.out.println("can t send update Message=> "+e.getMessage());
		}

	}
}

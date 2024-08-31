package com.paytonkawa.commande_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytonkawa.commande_service.controller.CommandController;
import com.paytonkawa.commande_service.dto.CommandDto;
import com.paytonkawa.commande_service.entity.Command;
import com.paytonkawa.commande_service.entity.Product;
import com.paytonkawa.commande_service.repo.CommandRepo;
import com.paytonkawa.commande_service.rest_client.ProductFeignClient;
import com.paytonkawa.commande_service.services.CommandServices;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CommandServiceApplicationTests {

    @Mock
    private CommandRepo commandRepo;

    @Mock
    private ProductFeignClient productFeignClient;

    @Autowired
    private MockMvc mockMvc;
    
    @Mock
    private KafkaTemplate<?, ?> kafkaTemplate;

    @InjectMocks
    private CommandServices commandServices;
    @Autowired
    private ObjectMapper objectMapper;
    
    @InjectMocks
    private CommandController commandController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testGetAllCommands() {
        Command command1 = new Command();
        Command command2 = new Command();
        List<Command> commands = Arrays.asList(command1, command2);

        when(commandRepo.findAll()).thenReturn(commands);

        ResponseEntity<List<Command>> response = commandServices.getAllCommands();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
    
    @Test
    void testAddCommand() {
        CommandDto commandDto = new CommandDto(1, 2);
        Command savedCommand = new Command();
        savedCommand.setCustomerId(1);
        savedCommand.setCustomerId(1);

        when(commandRepo.save(any(Command.class))).thenReturn(savedCommand);
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("product1");
        product1.setStock(100);
        product1.setQuantity(10);
        when(productFeignClient.getProductById(anyInt())).thenReturn(new ResponseEntity<Product>( product1, HttpStatus.OK));
        when(commandRepo.findById(anyInt())).thenReturn(Optional.of(savedCommand));

        ResponseEntity<Command> response = commandServices.addCommand(1, commandDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getId());
    }
    
    @Test
    void testAddProductToCart() {
        CommandDto commandDto = new CommandDto(1, 2);
        Command command = new Command();
        command.setCustomerId(1);

        Product product = new Product();
        product.setId(1);
        product.setStock(10);

        when(commandRepo.findById(anyInt())).thenReturn(Optional.of(command));
        when(productFeignClient.getProductById(anyInt())).thenReturn(new ResponseEntity<>(product, HttpStatus.OK));

        ResponseEntity<Command> response = commandServices.addProductToCart(1, commandDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getId());
    }
    
    @Test
    void testValidateCommand() {
        Command command = new Command();
        command.setCustomerId(1);
        Product product = new Product();
        product.setId(1);
        product.setName("Product1");
        product.setStock(10);
        product.setQuantity(5);
        command.getProducts().add(product);

        when(commandRepo.findById(anyInt())).thenReturn(Optional.of(command));
        when(productFeignClient.getProductById(anyInt())).thenReturn(new ResponseEntity<>(product, HttpStatus.OK));

        ResponseEntity<Map<String, String>> response = commandServices.validateCommand(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().get("message").contains("purchased successfully"));
    }
    
    @Test
    void testDeleteCommand() {
        doNothing().when(commandRepo).deleteById(anyInt());

        ResponseEntity<String> response = commandServices.deleteCommand(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("command with id=>1 deleted successfully", response.getBody());
    }

    @Test
    void testGetProductById() {
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("product1");
        product1.setStock(100);
        product1.setQuantity(10);

        when(productFeignClient.getProductById(anyInt())).thenReturn(new ResponseEntity<>(product1, HttpStatus.OK));

        Product result = commandServices.getProductById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("product1", result.getName());
    }
    /*   
    @Test
    void testGetAllCommandsController() throws Exception {
        Command command1 = new Command();
        Command command2 = new Command();
        List<Command> commands = Arrays.asList(command1, command2);
        this.commandRepo.saveAll(commands);
        //when(commandServices.getAllCommands()).thenReturn(ResponseEntity.ok(commands));

        mockMvc.perform(get("/command"))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void testCreateCommandController() throws Exception {
        CommandDto commandDto = new CommandDto(1, 2);
        Command savedCommand = new Command();
        savedCommand.setCustomerId(1);

        when(commandServices.addCommand(anyInt(), any(CommandDto.class))).thenReturn(ResponseEntity.ok(savedCommand));

        mockMvc.perform(post("/commands/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commandDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testAddProductToCommandController() throws Exception {
        CommandDto commandDto = new CommandDto(1, 2);
        Command command = new Command();

        when(commandServices.addProductToCart(anyInt(), any(CommandDto.class))).thenReturn(ResponseEntity.ok(command));

        mockMvc.perform(put("/command/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commandDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCommandByIdController() throws Exception {
        Command command = new Command();
        command.setCustomerId(1);

        when(commandServices.getCommandById(anyInt())).thenReturn(ResponseEntity.ok(command));

        mockMvc.perform(get("/command/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1));
    }

    @Test
    void testGetCommandProductsController() throws Exception {
        Product product1 = new Product();
        product1.setName("product1");
        Product product2 = new Product();
        product2.setName("product2");
        List<Product> products = Arrays.asList(product1, product2);

        when(commandServices.getCommandProducts(anyInt())).thenReturn(ResponseEntity.ok(products));

        mockMvc.perform(get("/command/1/products"))
                .andExpect(status().isOk());
               
    }

    @Test
    void testValidateCommandController() throws Exception {
        when(commandServices.validateCommand(anyInt())).thenReturn(ResponseEntity.ok(Map.of("message", "command validated")));

        mockMvc.perform(post("/command/1/validate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("command validated"));
    }

    @Test
    void testDeleteCommandController() throws Exception {
        when(commandServices.deleteCommand(anyInt())).thenReturn(ResponseEntity.ok("command deleted"));

        mockMvc.perform(delete("/command/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("command deleted"));
    }
    */
}

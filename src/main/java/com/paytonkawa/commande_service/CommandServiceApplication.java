package com.paytonkawa.commande_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.paytonkawa.commande_service.entity.Command;
import com.paytonkawa.commande_service.repo.CommandRepo;

@SpringBootApplication
@EnableFeignClients
public class CommandServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommandServiceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner start(CommandRepo CommandRepo){
		return args->{
			Command command = new Command();
			command.setCustomerId(1);
			CommandRepo.save(command);
			System.out.println("ccommand saved successfully");
		};
}
	
}

package com.paytonkawa.commande_service.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.paytonkawa.commande_service.dto.updateProductStockDto;

@Configuration
public class KafkaProducerConfig {

	@Value("${kafka.server.host}")
	private String bootsrapServer;
	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("spring2").build();
	}
	
	@Bean 
	public Map<String,Object> producerConfiguration(){
		Map<String,Object> config = new HashMap<String,Object>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootsrapServer);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,org.springframework.kafka.support.serializer.JsonSerializer.class);
		return config;
	}		
	@Bean
	public ProducerFactory<String, updateProductStockDto> producerFactory(){
		return new DefaultKafkaProducerFactory<>(producerConfiguration());
	}
	@Bean
	public KafkaTemplate<String,updateProductStockDto> kafkaTemplate() {
		return new KafkaTemplate<String,updateProductStockDto>(producerFactory());
	}
}

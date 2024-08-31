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


	@Value("${kafka.topic}")
	private String kafkaTopic;
	@Bean
	public NewTopic topic() {
		return TopicBuilder.name(kafkaTopic).build();
	}
}

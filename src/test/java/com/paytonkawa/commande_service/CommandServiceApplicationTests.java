package com.paytonkawa.commande_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytonkawa.commande_service.repo.CommandRepo;
import com.paytonkawa.commande_service.services.CommandServices;

@SpringBootTest
class CommandServiceApplicationTests {

    @Autowired
    private CommandRepo commandRepo;
    @Autowired
    private CommandServices commandServices;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testMainMethod() {
        CommandServiceApplication.main(new String[]{}); 
    }
    
    @BeforeEach
    void setUp() {

    }

}

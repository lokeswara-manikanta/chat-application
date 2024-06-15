package com.example.chatapplication.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
	
	@KafkaListener(topics = "chat", groupId = "chat_group")
    public void consume(String message) {
        System.out.println("Received message: " + message);
    }

}

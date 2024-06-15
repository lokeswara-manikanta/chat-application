package com.example.chatapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.chatapplication.model.ChatMessage;

@Service
public class ChatService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private static final String TOPIC = "chat";

	public void sendMessage(ChatMessage message) {
		kafkaTemplate.send(TOPIC,"chat_group", message.getSender() + ":" + message.getContent());
	}
}

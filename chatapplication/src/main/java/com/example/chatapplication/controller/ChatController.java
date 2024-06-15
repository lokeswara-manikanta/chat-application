package com.example.chatapplication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.chatapplication.model.ChatMessage;
import com.example.chatapplication.model.User;
import com.example.chatapplication.service.ChatService;
import com.example.chatapplication.service.KafkaConsumerService;
import com.example.chatapplication.service.UserService;

@Controller
public class ChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private KafkaConsumerService kafkaConsumerService; // Inject KafkaConsumerService

    private List<String> messages = new ArrayList<>();

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        userService.register(user);
        model.addAttribute("message", "User registered successfully!");
        return "login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model) {
        User loggedInUser = userService.login(user.getUsername(), user.getPassword());
        if (loggedInUser != null) {
            model.addAttribute("username", loggedInUser.getUsername());
            model.addAttribute("chatMessage", new ChatMessage());
            model.addAttribute("messages", messages);
            return "chat";
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @PostMapping("/send")
    public String sendMessage(@ModelAttribute ChatMessage message, Model model) {
        chatService.sendMessage(message);
        messages.add(message.getSender() + ": " + message.getContent()); // Add sent message to list for display
        model.addAttribute("username", message.getSender());
        model.addAttribute("chatMessage", new ChatMessage());
        model.addAttribute("messages", messages);
        return "chat";
    }

    // No need for @KafkaListener in the controller, keep this in KafkaConsumerService
}

package com.startnow.blog.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.startnow.blog.model.ChatMessage;
import com.startnow.blog.service.ChatService;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/sendMessage")
    public void sendMessage(ChatMessage message) {
    	System.out.println(message);
        chatService.saveMessage(message);
        messagingTemplate.convertAndSend("/topic/messages/" + message.getReceiver(), message);
        messagingTemplate.convertAndSend("/topic/messages/" + message.getSender(), message); 
    }
    
    @MessageMapping("/sendGroupMessage")
    public void sendGroupMessage(ChatMessage message) {
        message.setReceiver("group"); // just to maintain consistency
        chatService.saveMessage(message);
        messagingTemplate.convertAndSend("/topic/group", message);
    }

}


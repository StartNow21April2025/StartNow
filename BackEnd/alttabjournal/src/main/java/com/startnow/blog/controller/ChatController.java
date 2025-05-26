package com.startnow.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.startnow.blog.model.ChatMessage;
import com.startnow.blog.service.ChatService;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/history")
    public List<ChatMessage> getChatHistory(@RequestParam String user1,
            @RequestParam String user2) {
        return chatService.getChatHistory(user1, user2);
    }

    @GetMapping("/api/chat/group-history")
    public List<ChatMessage> getGroupChatHistory() {
        return chatService.getAllMessages(); // Or filter with no receiver
    }
}


package com.startnow.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.startnow.blog.model.ChatMessage;
import com.startnow.blog.service.ChatService;

@RestController
@RequestMapping("/api/group")
public class GroupChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/messages")
    public List<ChatMessage> getGroupMessages() {
        return chatService.getGroupMessages();
    }
}

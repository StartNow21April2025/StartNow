package com.startnow.blog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.startnow.blog.model.ChatMessage;

@Service
public class ChatService {
    private final List<ChatMessage> messages = new CopyOnWriteArrayList<>();

    public void saveMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        messages.add(message);
    }

    public List<ChatMessage> getChatHistory(String user1, String user2) {
        return messages.stream()
            .filter(msg -> (msg.getSender().equals(user1) && msg.getReceiver().equals(user2)) ||
                           (msg.getSender().equals(user2) && msg.getReceiver().equals(user1)))
            .collect(Collectors.toList());
    }
    
    public List<ChatMessage> getAllMessages() {
        return new ArrayList<>(messages);
    }
    
    public List<ChatMessage> getGroupMessages() {
        return messages.stream()
            .filter(msg -> "group".equals(msg.getReceiver()))
            .collect(Collectors.toList());
    }
}


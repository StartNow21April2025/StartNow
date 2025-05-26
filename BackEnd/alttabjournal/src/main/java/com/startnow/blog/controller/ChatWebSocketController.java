package com.startnow.blog.controller;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.messaging.MessagingException;

import com.startnow.blog.model.ChatMessage;
import com.startnow.blog.service.ChatService;

@Controller
public class ChatWebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketController.class);
    private static final String TOPIC_MESSAGES_FORMAT = "/topic/messages/%s";
    private static final String TOPIC_GROUP = "/topic/group";
    private static final String GROUP_RECEIVER = "group";

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @Autowired
    public ChatWebSocketController(SimpMessagingTemplate messagingTemplate,
            ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(ChatMessage message) {
        try {
            validateMessage(message);
            logger.debug("Processing private message: {}", message);

            chatService.saveMessage(message);

            // Send to receiver
            String receiverDestination =
                    String.format(TOPIC_MESSAGES_FORMAT, message.getReceiver());
            messagingTemplate.convertAndSend(receiverDestination, message);

            // Send to sender
            String senderDestination = String.format(TOPIC_MESSAGES_FORMAT, message.getSender());
            messagingTemplate.convertAndSend(senderDestination, message);

            logger.info("Message successfully sent between {} and {}", message.getSender(),
                    message.getReceiver());

        } catch (IllegalArgumentException e) {
            logger.warn("Invalid message received: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error processing message: {}", message, e);
            throw new MessagingException("Failed to process message", e);
        }
    }

    @MessageMapping("/sendGroupMessage")
    public void sendGroupMessage(ChatMessage message) {
        try {
            validateMessage(message);
            logger.debug("Processing group message: {}", message);

            message.setReceiver(GROUP_RECEIVER);
            chatService.saveMessage(message);
            messagingTemplate.convertAndSend(TOPIC_GROUP, message);

            logger.info("Group message successfully sent from {}", message.getSender());

        } catch (IllegalArgumentException e) {
            logger.warn("Invalid group message received: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error processing group message: {}", message, e);
            throw new MessagingException("Failed to process group message", e);
        }
    }

    private void validateMessage(ChatMessage message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        if (!StringUtils.hasText(message.getSender())) {
            throw new IllegalArgumentException("Sender cannot be empty");
        }

        if (!StringUtils.hasText(message.getContent())) {
            throw new IllegalArgumentException("Message content cannot be empty");
        }

        if (message.getContent().length() > 1000) { // Example limit
            throw new IllegalArgumentException("Message content exceeds maximum length");
        }

        // For private messages, validate receiver
        if (!GROUP_RECEIVER.equals(message.getReceiver())
                && !StringUtils.hasText(message.getReceiver())) {
            throw new IllegalArgumentException("Receiver cannot be empty for private messages");
        }
    }
}

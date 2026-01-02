package com.chatApp.chatapplication.controller;

import com.chatApp.chatapplication.Message;
import com.chatApp.chatapplication.WebSocketSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final WebSocketSessionManager sessionManager;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate, WebSocketSessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message")
    public void handleMessage(Message message) {
        // Validate message before processing
        if (message == null || message.getUser() == null || message.getMessage() == null) {
            System.out.println("Received invalid message, ignoring.");
            return;
        }

        System.out.println("Received message from user: " + message.getUser() + ": " + message.getMessage());
        messagingTemplate.convertAndSend("/topic/messages", message);
        System.out.println("Sent message to /topic/messages: " + message.getUser() + ": " + message.getMessage());
    }

    @MessageMapping("/connect")
    public void connectUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Invalid username provided for connection");
            return;
        }

        boolean added = sessionManager.addUsername(username);
        if (added) {
            sessionManager.broadcastActiveUsernames();
            System.out.println(username + " connected");
        } else {
            System.out.println(username + " connection attempt - username already exists");
            // Still broadcast to ensure client gets updated list
            sessionManager.broadcastActiveUsernames();
        }
    }

    @MessageMapping("/disconnect")
    public void disconnectUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Invalid username provided for disconnection");
            return;
        }

        boolean removed = sessionManager.removeUsername(username);
        sessionManager.broadcastActiveUsernames();
        if (removed) {
            System.out.println(username + " disconnected");
        } else {
            System.out.println(username + " disconnection - username was not in active list");
        }
    }

    @MessageMapping("/request-users")
    public void requestUsers(){
        sessionManager.broadcastActiveUsernames();
        System.out.println("Requesting Users");
    }
}
package com.chatApp.chatapplication.Client;

import com.chatApp.chatapplication.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

public class MyStompClient {
    private StompSession session;
    private String username;

    public MyStompClient(MessageListener messageListener, String username) {
        this.username = username;

        // Connect in a background thread to avoid blocking Swing
        new Thread(() -> {
            try {
                List<Transport> transports = new ArrayList<>();
                transports.add(new WebSocketTransport(new StandardWebSocketClient()));

                SockJsClient sockJsClient = new SockJsClient(transports);
                WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
                stompClient.setMessageConverter(new MappingJackson2MessageConverter());

                StompSessionHandler sessionHandler = new MyStompSessionHandler(messageListener, username);

                // Use HTTP for SockJS
                session = stompClient.connect("http://localhost:8080/ws", sessionHandler).get();

                System.out.println("Connected to WebSocket server as " + username);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(Message message) {
        if (session != null && session.isConnected()) {
            session.send("/app/message", message);
            System.out.println("Message Sent: " + message.getMessage());
        } else {
            System.out.println("Cannot send message: not connected yet.");
        }
    }

    public void disconnectUser(String username) {
        session.send("/app/disconnect", username);
        System.out.println("Disconnect User: " + username);
    }
}

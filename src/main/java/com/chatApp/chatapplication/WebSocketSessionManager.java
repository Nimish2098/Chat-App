package com.chatApp.chatapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class WebSocketSessionManager {
    // Use CopyOnWriteArraySet for thread-safe operations and automatic duplicate prevention
    private final CopyOnWriteArraySet<String> activeUsernames = new CopyOnWriteArraySet<>();
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketSessionManager(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Adds a username to the active users set.
     * Thread-safe and prevents duplicates automatically.
     *
     * @param username the username to add
     * @return true if the username was added, false if it already exists
     */
    public boolean addUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        boolean added = activeUsernames.add(username.trim());
        return added;
    }

    /**
     * Removes a username from the active users set.
     * Thread-safe operation.
     *
     * @param username the username to remove
     * @return true if the username was removed, false if it didn't exist
     */
    public boolean removeUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        boolean removed = activeUsernames.remove(username.trim());
        return removed;
    }

    /**
     * Broadcasts the current list of active usernames to all connected clients.
     * Thread-safe - creates a snapshot of the current usernames.
     */
    public void broadcastActiveUsernames() {
        // Create a thread-safe copy as ArrayList for consistent JSON serialization
        List<String> usernamesList = new ArrayList<>(activeUsernames);
        messagingTemplate.convertAndSend("/topic/users", usernamesList);
        System.out.println("Broadcasting active users to /topic/users " + usernamesList);
    }

    /**
     * Gets the current count of active users.
     * Thread-safe operation.
     *
     * @return the number of active users
     */
    public int getActiveUserCount() {
        return activeUsernames.size();
    }

    /**
     * Checks if a username is currently active.
     * Thread-safe operation.
     *
     * @param username the username to check
     * @return true if the username is active, false otherwise
     */
    public boolean isUsernameActive(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return activeUsernames.contains(username.trim());
    }

    /**
     * Gets a copy of all active usernames.
     * Thread-safe - returns a snapshot.
     *
     * @return a new ArrayList containing all active usernames
     */
    public List<String> getActiveUsernames() {
        return new ArrayList<>(activeUsernames);
    }
}
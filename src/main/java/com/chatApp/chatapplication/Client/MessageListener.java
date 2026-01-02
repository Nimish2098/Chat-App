package com.chatApp.chatapplication.Client;

import com.chatApp.chatapplication.Message;

import java.util.ArrayList;

public interface MessageListener {
    void onMessageRecieve(Message message);
    void onActiveUsersUpdated(ArrayList<String> users);
}

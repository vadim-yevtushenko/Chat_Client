package com.example.chat;

import java.util.ArrayList;

public class Model {
    private String message;
    private ArrayList<String> textMessages = new ArrayList<>();
    private ArrayList<String> clients = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getTextMessages() {
        return textMessages;
    }

    public void setTextMessages(ArrayList<String> textMessages) {
        this.textMessages = textMessages;
    }

    public ArrayList<String> getClients() {
        return clients;
    }

    public void setClients(ArrayList<String> clients) {
        this.clients = clients;
    }
}

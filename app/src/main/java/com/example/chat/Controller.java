package com.example.chat;

import android.content.Intent;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Controller {

    private Socket socket;
    private Scanner scannerIn;
    private PrintWriter printWriter;
    private Model model;
    private ChatActivity chatActivity;
    private String ip;
    private String port;


    public Controller(ChatActivity chatActivity) {
        model = new Model();
        this.chatActivity = chatActivity;
        this.ip = chatActivity.getIp();
        this.port = chatActivity.getPort();

    }

    public Model getModel() {
        return model;
    }

    public void run() {

        Thread thread = new Thread(() -> {

            try {

//                socket = new Socket("192.168.1.100", 6789);
                socket = new Socket(ip, Integer.parseInt(port));

                printWriter = new PrintWriter(socket.getOutputStream());
                scannerIn = new Scanner(socket.getInputStream());

            } catch (Exception e) {
                chatActivity.runOnUiThread(() -> {
                    Intent intent = new Intent(chatActivity, MainActivity.class);
                    String[] strings = new String[] {ip, port, "error connection"};
                    intent.putExtra(MainActivity.KEY_ERROR, strings);
                    chatActivity.startActivity(intent);
                    chatActivity.finish();
                });

            }

            if (scannerIn != null) {
                while (scannerIn.hasNextLine()) {
                    String message = scannerIn.nextLine();
                    if (message.startsWith(" Online clients:")) {
                        incomingClients(message);
                    } else {
                        incomingMessage(message);
                    }
                }
            }
        });
        thread.start();


    }

    public void sendMessage(String message) {
        new Thread(() -> {
            printWriter.println(message);
            printWriter.flush();
        }).start();

    }

    public void incomingMessage(String message) {

        model.getTextMessages().add(message);

        chatActivity.runOnUiThread(() -> {
            chatActivity.updateMessageField();
        });
    }

    public void incomingClients(String message) {
        String[] list = message.split(",");
        model.setClients(new ArrayList<>(Arrays.asList(list)));
        chatActivity.runOnUiThread(() -> {
            chatActivity.updateOnlineClientsField();
        });
    }
}

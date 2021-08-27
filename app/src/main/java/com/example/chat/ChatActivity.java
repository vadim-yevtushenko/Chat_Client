package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private ListView lvChat;
    private ListView lvOnline;
    private EditText etMessage;
    private Button bSend;
    private ProgressBar pbConnect;
    private Controller controller;


    private MessageAdapter messageAdapter;
    private OnlineClientsAdapter onlineClientsAdapter;
    private ArrayList<Map<String, String>> messages;
    private ArrayList<Map<String, String>> onlineClients;
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_CLIENTS = "CLIENTS";
    private String[] ipPort;
    private String ip;
    private String port;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        if (intent != null){
            ipPort = (String[]) intent.getSerializableExtra(MainActivity.KEY_IP_PORT);
        }
        ip = ipPort[0];
        port = ipPort[1];

        initViews();
        controller = new Controller(this);
        messages = new ArrayList<>();
        onlineClients = new ArrayList<>();

        bSend.setOnClickListener(this::sendMessage);
        controller.run();
    }

    private void fillMessagesList() {
        messages = new ArrayList<>();
        Map<String, String> data;
        for (String message : controller.getModel().getTextMessages()) {
            data = new HashMap<>();
            data.put(KEY_MESSAGE, message);

            messages.add(data);
        }
    }

    private void fillClientsList() {
        onlineClients = new ArrayList<>();
        Map<String, String> data;
        for (String client : controller.getModel().getClients()) {
            data = new HashMap<>();
            data.put(KEY_CLIENTS, client);

            onlineClients.add(data);
        }
    }

    private void createListMessages() {
        messageAdapter = new MessageAdapter(this,
                messages,
                R.layout.message_item,
                new String[]{KEY_MESSAGE},
                new int[]{android.R.id.text1});
        lvChat.setAdapter(messageAdapter);
    }

    private void createListClients() {
        onlineClientsAdapter = new OnlineClientsAdapter(this,
                onlineClients,
                R.layout.online_clients_item,
                new String[]{KEY_CLIENTS},
                new int[]{android.R.id.text1});
        lvOnline.setAdapter(onlineClientsAdapter);
    }

    private void sendMessage(View view) {
        controller.sendMessage(etMessage.getText().toString());
        etMessage.setText("");
    }

    private void initViews() {
        lvChat = findViewById(R.id.lvChat);
        lvOnline = findViewById(R.id.lvOnline);
        etMessage = findViewById(R.id.etMessage);
        bSend = findViewById(R.id.bSend);
        pbConnect = findViewById(R.id.pbConnect);

    }

    public void updateMessageField() {
        fillMessagesList();
        createListMessages();

    }

    public void updateOnlineClientsField() {
        fillClientsList();
        createListClients();

    }

    public void setInvisibleProgressBar(){
        pbConnect.setVisibility(View.INVISIBLE);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
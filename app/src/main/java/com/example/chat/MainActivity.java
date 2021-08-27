package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etIp;
    private EditText etPort;
    private Button bConnect;
    private String[] ipPort;
    private String[] errors;
    public static final String KEY_IP_PORT = "ip_port";
    public static final String KEY_ERROR = "error";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent != null) {
            errors = (String[]) intent.getSerializableExtra(KEY_ERROR);
            if (errors != null) {
                Toast toast = Toast.makeText(this, errors[2], Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 15, 150);
                toast.show();
            }
        }



        initViews();

        bConnect.setOnClickListener(this::connect);

    }

    private void connect(View view) {
        ipPort = new String[2];
        ipPort[0] = etIp.getText().toString();
        ipPort[1] = etPort.getText().toString();
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(KEY_IP_PORT, ipPort);
        startActivity(intent);
        finish();
    }

    private void initViews() {
        etIp = findViewById(R.id.etIp);
        etPort = findViewById(R.id.etPort);
        bConnect = findViewById(R.id.bConnect);
        if (errors != null){
            etIp.setText(errors[0]);
            etPort.setText(errors[1]);
        }

    }


}
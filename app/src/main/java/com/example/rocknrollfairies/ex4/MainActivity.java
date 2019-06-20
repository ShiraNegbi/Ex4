package com.example.rocknrollfairies.ex4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connect(View view) {
        EditText editTextIp = (EditText)findViewById(R.id.ip);
        EditText editTextPort = (EditText)findViewById(R.id.port);
        Intent intent = new Intent(this, Joystick.class);
        String msgIp = editTextIp.getText().toString();
        String msgPort = editTextPort.getText().toString();
        intent.putExtra("messageIp", msgIp);
        intent.putExtra("messagePort", msgPort);
        startActivity(intent);
    }
}

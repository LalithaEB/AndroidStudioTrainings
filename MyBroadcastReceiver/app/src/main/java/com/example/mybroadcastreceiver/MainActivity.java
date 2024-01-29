package com.example.mybroadcastreceiver;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver receiver = new Receiver();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Lalitha", "broadcast_app: onCreate()");

        Button sendButton = findViewById(R.id.sendButton);
        Button sendOtherAppButton = findViewById(R.id.sendOtherAppButton);

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.MY_BROADCAST");
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        getApplicationContext().registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED);

        sendButton.setOnClickListener(view -> {
            broadcastIntent();
        });

        sendOtherAppButton.setOnClickListener(view -> {
            broadcastIntent();
        });
    }

    private void broadcastIntent() {
        Intent intent = new Intent();
//        intent.setPackage("com.example.mybroadcastreceiver");
        intent.setAction("com.example.MY_BROADCAST");
        sendBroadcast(intent);
    }
}
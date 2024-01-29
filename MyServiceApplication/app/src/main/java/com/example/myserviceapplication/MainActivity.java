package com.example.myserviceapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button displayButton;
    Button stopButton;
    BroadcastReceiver receiver = new Receiver();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Lalitha", "service_app: onCreate()");

        displayButton = findViewById(R.id.displayButton);
        stopButton = findViewById(R.id.stopButton);

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.MY_BROADCAST");
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        getApplicationContext().registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED);

        displayButton.setOnClickListener(view -> startService(new Intent(this, MyService.class)));

        stopButton.setOnClickListener(view -> stopService(new Intent(this, MyService.class)));
    }
}
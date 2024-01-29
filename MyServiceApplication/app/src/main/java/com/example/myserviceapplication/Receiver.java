package com.example.myserviceapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Lalitha", "service_app: Broadcast received to service app");
        if(Objects.equals(intent.getAction(), "com.example.MY_BROADCAST")) {
            Log.d("Lalitha", "service_app: My Broadcast received CUSTOM");
            Toast.makeText(context,"service_app- My broadcast received", Toast.LENGTH_SHORT).show();
        } else if(Objects.equals(intent.getAction(), Intent.ACTION_AIRPLANE_MODE_CHANGED)){
            Log.d("Lalitha", "service_app: Airplane Broadcast received");
            Toast.makeText(context, "service_app- Airplane mode changed", Toast.LENGTH_SHORT).show();
        }
    }
}

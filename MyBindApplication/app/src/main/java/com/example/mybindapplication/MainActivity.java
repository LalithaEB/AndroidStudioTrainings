package com.example.mybindapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    MyService service;
    Boolean mIsBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService();
    }

    private void startService() {
        Intent bindServiceIntent = new Intent(this, MyService.class);
        bindService(bindServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mIsBound){
            unbindService(serviceConnection);
            Log.d("My Service", "Service Disconnected");
            mIsBound = false;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("My Service", "Service Connected");

            MyService.MyBinder binder = (MyService.MyBinder)iBinder;
            service = binder.getService();
            mIsBound = true;
            getSumFromService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("My Service", "Service Disconnected");
            mIsBound = false;
        }
    };

    private void getSumFromService() {
        Log.d("My Service", "Sum from service is " + service.getSum());
    }
}
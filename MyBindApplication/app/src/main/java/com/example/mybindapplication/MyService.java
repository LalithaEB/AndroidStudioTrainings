package com.example.mybindapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {

private final IBinder binder = new MyBinder();
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    public int getSum() {
        return 4 + 5;
    }
}
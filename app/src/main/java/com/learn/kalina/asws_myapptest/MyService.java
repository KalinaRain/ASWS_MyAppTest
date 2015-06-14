package com.learn.kalina.asws_myapptest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by KalinaRain on 2015/4/3.
 */
public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("log", "onBind--------------");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("log", "onUnbind--------------");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("log", "the service has been created--------------");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("log", "the service has been started--------------");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("log", "the service has been Destroyed--------------");
    }
}

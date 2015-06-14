package com.kalinarain.notificationtest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by KalinaRain on 2015/4/2.
 */
public class myservice extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("log", "onBind------");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("log", "onUnbind------");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        Log.d("log", "the service has been created------");
        super.onCreate();

    }

    //after Api 2.0
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("log", "the service has been started------");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("log", "the service has been destroyed------");
        super.onDestroy();
    }
}

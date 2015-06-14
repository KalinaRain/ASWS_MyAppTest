package com.learn.kalina.asws_myapptest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private Button bt_start;
    private Button bt_stop;
    private Button bt_bind;
    private Button bt_unbind;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        showNotification();
        bt_start.setOnClickListener(listener);
        bt_stop.setOnClickListener(listener);
        bt_bind.setOnClickListener(listener);
        bt_unbind.setOnClickListener(listener);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("log", "the service has connected");
        }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("log", "the service has been disconnected");
        }
    };

    private void initView() {
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_stop = (Button) findViewById(R.id.bt_stop);
        bt_bind = (Button) findViewById(R.id.bt_bind);
        bt_unbind = (Button) findViewById(R.id.bt_unbind);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, MyService.class);
            switch (v.getId()) {
                case R.id.bt_start:
                    startService(intent);
                    break;
                case R.id.bt_stop:
                    stopService(intent);
                    break;
                case R.id.bt_bind:
                    bindService(intent, conn, Service.BIND_AUTO_CREATE);
                    break;
                case R.id.bt_unbind:
                    unbindService(conn);

                default:
                    break;
            }
        }
    };

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.iconfontshu)
                .setContentTitle("这是一个标题").setContentText("这是notification的具体的内容");
        Intent intent = new Intent(this, SecondActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
        stackBuilder.addParentStack(SecondActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        notificationManager.notify(0,builder.build());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

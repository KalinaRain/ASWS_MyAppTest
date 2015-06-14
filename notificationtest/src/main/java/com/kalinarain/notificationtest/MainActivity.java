package com.kalinarain.notificationtest;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private NotificationManager notificationManager;
    private Button bt_bind;
    private Button bt_unbind;
    private Button bt_start;
    private Button bt_stop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitView();

        bt_bind.setOnClickListener(listener);
        bt_unbind.setOnClickListener(listener);


    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("log", "连接成功！");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("log", "断开连接！");
        }
    };

    private void InitView() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        bt_bind = (Button) findViewById(R.id.bt_bind);
        bt_unbind = (Button) findViewById(R.id.bt_unbind);
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_stop = (Button) findViewById(R.id.bt_stop);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, myservice.class);
            switch (v.getId()) {
                case R.id.bt_bind:
                    bindService(intent, conn, Service.BIND_AUTO_CREATE);//the third arg can be 0(do not create auto)
                    break;//!!!!!!!!
                case R.id.bt_unbind:
                    unbindService(conn);
                    break;
                case R.id.bt_start:
                    startService(intent);
                    break;//!!!!!!!!
                case R.id.bt_stop:
                    stopService(intent);
                    break;
                default:
                    break;
            }
        }
    };


    private void notificationBuild() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.iconfontshu).setContentTitle("这是一个标题").setContentText("具体的内容");
//        mBuilder.setStyle()
        // Creates an explicit intent for an Activity
        Intent intent = new Intent(this, ResultActivity.class);
        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ResultActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(0, mBuilder.build());

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

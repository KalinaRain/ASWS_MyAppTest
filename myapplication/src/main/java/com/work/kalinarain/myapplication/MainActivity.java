package com.work.kalinarain.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {


    private final String MyInfo = "App";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.main.a");
//                startActivity(intent);
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:18908353476"));
                startActivity(intent);
            }
        });

        RelativeLayout ll = (RelativeLayout)findViewById(R.id.re_pareent);
        ViewParent viewParent = ll.getParent();

        Log.d(MyInfo, "onCreate-----------------------------");
        Log.d("viewParent`s parent ", viewParent.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MyInfo, "onStart-----------------------------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MyInfo, "onResume-----------------------------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MyInfo, "onPause-----------------------------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MyInfo, "onStop-----------------------------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MyInfo, "onDestroy-----------------------------");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(MyInfo, "onRestart-----------------------------");
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

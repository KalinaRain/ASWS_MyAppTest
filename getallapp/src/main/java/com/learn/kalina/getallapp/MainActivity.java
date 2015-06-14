package com.learn.kalina.getallapp;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    ArrayList<AppInfo> appLists = new ArrayList<AppInfo>();
    List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
    private ListView listView;
    private ImageView img_AppIcon;
    private TextView appName;
    private TextView packName;
    private TextView tv_Open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onInit();
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            AppInfo appInfo = new AppInfo();
            appInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            appInfo.packagName = packageInfo.packageName;
            appInfo.versionName = packageInfo.versionName;
            appInfo.versionCode = packageInfo.versionCode;
            appInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                //非系统应用
            } else {
                //系统应用
            }
            appLists.add(appInfo);
//            startActivity();
        }
        listView.setAdapter(new MyBaseAdapter(MainActivity.this));

    }

    private void onInit() {
        listView = (ListView) findViewById(R.id.listView);
        img_AppIcon = (ImageView) findViewById(R.id.img_appIcon);
        appName = (TextView) findViewById(R.id.appName);
        packName = (TextView) findViewById(R.id.packageName);
        tv_Open = (TextView) findViewById(R.id.tvOpen);

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

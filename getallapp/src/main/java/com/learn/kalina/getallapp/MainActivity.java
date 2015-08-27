package com.learn.kalina.getallapp;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

//    public static final String TAG = "GetAllApp";
    ArrayList<AppInfo> appLists = new ArrayList<AppInfo>();
    ArrayList<AppInfo> sysappLists = new ArrayList<AppInfo>();
    ArrayList<AppInfo> nonsysappLists = new ArrayList<AppInfo>();
    List<PackageInfo> packages;
    private ListView listView;
    private Button bt_AllAPP;//所有应用
    private Button bt_SysAPP;//系统应用
    private Button bt_NonSysAPP;//非系统应用
    private ImageView img_AppIcon;
    private TextView appName;
    private TextView packageName;
    private TextView tv_Open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onInit();
for (int i = 0; i < packages.size(); i++) {
    PackageInfo packageInfo = packages.get(i);
    AppInfo appInfo = new AppInfo();
    appInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());//获取应用的图标
    appInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();//获取应用名称
    appInfo.packagName = packageInfo.packageName;//获取包名
    appInfo.versionName = packageInfo.versionName;//获取版本名称
    appInfo.versionCode = packageInfo.versionCode;//获取版本号

    appLists.add(appInfo);//将该App的信息假如list中，以便后面取用
    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
        Log.d("a" + " flags:", packageInfo.applicationInfo.flags+"");
        //非系统应用
        nonsysappLists.add(appInfo);
    } else {
        //系统应用
        sysappLists.add(appInfo);
    }

}
        Log.d("系统应用的数量：", sysappLists.size()+"");
        Log.d("非系统应用的数量：", nonsysappLists.size() + "");

        //默认是非系统应用
        listView.setAdapter(new MyBaseAdapter(MainActivity.this,nonsysappLists));

    }

    private void onInit() {
        listView = (ListView) findViewById(R.id.listView);
        bt_AllAPP = (Button) findViewById(R.id.bt_allApp);
        bt_SysAPP = (Button) findViewById(R.id.bt_sysApp);
        bt_NonSysAPP = (Button) findViewById(R.id.bt_nonSysApp);
        bt_AllAPP.setOnClickListener(btClicklistener);
        bt_SysAPP.setOnClickListener(btClicklistener);
        bt_NonSysAPP.setOnClickListener(btClicklistener);

        packages = getPackageManager().getInstalledPackages(0);

    }


    View.OnClickListener btClicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_allApp:
                    listView.setAdapter(new MyBaseAdapter(MainActivity.this,appLists));
                    listView.deferNotifyDataSetChanged();
                    break;
                case R.id.bt_sysApp:
                    listView.setAdapter(new MyBaseAdapter(MainActivity.this,sysappLists));
                    listView.deferNotifyDataSetChanged();
                    break;
                case R.id.bt_nonSysApp:
                    listView.setAdapter(new MyBaseAdapter(MainActivity.this,nonsysappLists));
                    listView.deferNotifyDataSetChanged();
                    break;
            }
        }
    };

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

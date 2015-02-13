package com.learn.kalina.getallapp;

import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by KalinaRain on 2015/2/12.
 */
public class AppInfo {
    public String appName = "";
    public String packagName = "";
    public String versionName = "";
    public int versionCode=0;
    public Drawable appIcon=null;
    public void print(){
        Log.d("myapps","Name:"+appName+" PackageName:"+packagName);
        Log.d("myapps","Name:"+appName+" VersionName:"+versionName);
        Log.d("myapps","Name:"+appName+" versioncode:"+versionCode);
    }


}

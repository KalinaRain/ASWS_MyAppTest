package com.learn.kalina.getallapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KalinaRain on 2015/2/16.
 */
public class MyBaseAdapter extends BaseAdapter {
    private Context context = null;
    private LayoutInflater inflater = null;
    private List<AppInfo> lists = new ArrayList<AppInfo>();

    public MyBaseAdapter(Context context, ArrayList<AppInfo> appInfos) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        lists = appInfos;//传入数据
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {

            convertView = inflater.inflate(R.layout.listitem_appinfo, null);

            viewHolder = new ViewHolder();
            viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.img_appIcon);
            viewHolder.tvAppName = (TextView) convertView.findViewById(R.id.appName);
            viewHolder.tvPackageName = (TextView) convertView.findViewById(R.id.packageName);
            viewHolder.tvOpen = (TextView) convertView.findViewById(R.id.tvOpen);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        /** 给每个App的各个信息 */
        final AppInfo tmpAppInfo = lists.get(position);
        viewHolder.imgIcon.setImageDrawable(tmpAppInfo.appIcon);
        viewHolder.tvAppName.setText(tmpAppInfo.appName);
        viewHolder.tvPackageName.setText(tmpAppInfo.packagName);
        if (tmpAppInfo.versionName == null) {
            viewHolder.tvVersionName.setText(tmpAppInfo.versionName);
        }
        if (tmpAppInfo.versionCode == 0) {
            viewHolder.tvVersionCode.setText(tmpAppInfo.versionCode);
        }
        /* 跳转到相对应的应用 */
        viewHolder.tvOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_open = context.getPackageManager().getLaunchIntentForPackage(tmpAppInfo.packagName);
//                intent_open.setClassName(tmpAppInfo.packagName, ActivityName);
                if (intent_open != null) {
                    context.startActivity(intent_open);
                } else {
                    Log.d("info", "intent_open is null ");
                }

            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView imgIcon;//App图标
        TextView tvAppName;//App名称
        TextView tvPackageName;//App包名
        TextView tvVersionName;//App版本名称
        TextView tvVersionCode;//App版本号
        TextView tvOpen;
    }
}

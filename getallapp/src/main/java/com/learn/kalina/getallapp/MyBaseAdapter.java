package com.learn.kalina.getallapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by KalinaRain on 2015/2/16.
 */
public class MyBaseAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Map<String, Object>> xf = new ArrayList<Map<String, Object>>();
    private Map<String, Object> df = new HashMap<String,Object>();

    public MyBaseAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = inflater.inflate(R.layout.listitem_nonsystemapp, null);
            viewHolder = new ViewHolder();
            viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.img_appIcon);
            viewHolder.tvAppName = (TextView) convertView.findViewById(R.id.appName);
            viewHolder.tvPackageName = (TextView) convertView.findViewById(R.id.packageName);
            viewHolder.tvOpen = (TextView) convertView.findViewById(R.id.tvOpen);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.imgIcon.setImageDrawable();
        viewHolder.tvOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
//                intent=context.getPackageManager().getLaunchIntentForPackage();
//                startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView imgIcon;
        TextView tvAppName;
        TextView tvPackageName;
        TextView tvOpen;
    }
}

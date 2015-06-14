package com.learn.kalina.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KalinaRain on 2015/5/1.
 */
public class QuestionMain extends Activity {
    private ListView listview;
    private ImageView img_ask;
    private LayoutInflater inflater;
    private String url = "http://redrock.hotwoo.cn/getQuestionList.php";
    private String result;
    private int num_item=0;
    private String title;
    private String content;
    private String author;
    private String date;
    List<Map<String,String>> list = new ArrayList<Map<String,String>>();
    Map<String,String> itemdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_question);
        initView();

        new Thread(new Runnable() {
            @Override
            public void run() {
                result = doHttpUrlConnection(0, url);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.get("status").toString() == "200") {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        num_item = jsonArray.length();
                        for (int i = 0; i <num_item; i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            title = item.getString("title");
                            content = item.getString("content");
                            author = item.getString("authorId");
                            date = item.getString("date");
                            itemdata = new HashMap<String, String>();
                            itemdata.put("title", title);
                            itemdata.put("content", content);
                            itemdata.put("authorId", author);
                            itemdata.put("date", date);
                            list.add(itemdata);
                            Log.d("data", list.toString());
                        }

                    } else {
                        Toast.makeText(QuestionMain.this,"出现异常",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        listview.setAdapter(new MyBaseAdapter());
    }

    private void initView() {
        inflater = LayoutInflater.from(QuestionMain.this);
        listview = (ListView) findViewById(R.id.listview);
        img_ask = (ImageView) findViewById(R.id.ask_question);
    }


    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView==null){
                convertView = inflater.inflate(R.layout.ques_item, parent,false);
                holder = new ViewHolder();
                //每个帖子发布的日期
                holder.list_release_date = (TextView) convertView.findViewById(R.id.list_release_date);
                //每个帖子发布的作者
                holder.list_release_author = (TextView) convertView.findViewById(R.id.list_release_author);
                //每个帖子的标题
                holder.list_title = (TextView) convertView.findViewById(R.id.list_title);
                //每个帖子大体的内容
                holder.list_content_manual = (TextView) convertView.findViewById(R.id.list_content_manual);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.list_release_date.setText(date);
            holder.list_release_author.setText(author);
            holder.list_title.setText(title);
            holder.list_content_manual.setText(content);
            return convertView;
        }

    }

    class ViewHolder{
        public TextView list_release_date;//发帖日期
        public TextView list_release_author;//发帖人
        public TextView list_title;//标题
        public TextView list_content_manual;//内容
    }

    public String doHttpUrlConnection(int page,String url){
        String result = null;
        try {
//            Map<String,String> data = new HashMap<String, String>();
//            data.put("page", page);
            String requestdata = "page=" + page;
            URL murl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) murl.openConnection();//打开连接
            conn.setConnectTimeout(3000);//设置最大连接时长
            //这两个方法在HttpURLConnection方法中必须要写
            conn.setDoInput(true);//允许输入（我认为就是说，允许服务器返回信息）
            conn.setDoOutput(true);//允许输出（我认为就是说，允许服务器发送请求信息）
            conn.setDefaultUseCaches(false);
            conn.setRequestMethod("POST");//两种方式
            //设置请求体的类型是文本类型
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度(不写应该没事)
            conn.setRequestProperty("Content-length", requestdata.length()+"");
            OutputStream out = conn.getOutputStream();
            out.write(requestdata.getBytes("UTF-8"));

            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                Log.d("info", "连接成功，状态码为" + conn.getResponseCode());
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                InputStream in = conn.getInputStream();//获取服务器返回的信息
                byte[] datas = new byte[1024];
                int len = 0;
                while ((len = in.read(datas)) != -1) {
                    outStream.write(datas, 0, len);
                }
                result= new String(outStream.toByteArray());
                Log.d("result",result);
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }
}

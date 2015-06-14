package com.learn.kalina.view;

import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
public class HttpHelper {


    /**
     * 注册与登录用的方法
     * @param username
     * @param password
     * @param url
     * @return 如果成功，则返回服务器的json数据；如果返回null，则代表请求失败
     */
    public static String doHttpUrlConnection(String username,String password,String url){
        String result = null;
        try {
            Map<String,String> data = new HashMap<String, String>();
            data.put("name", username);
            data.put("password", password);
            String requestdata = setRequestEntity(data);
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
                Log.d("info", "连接成功，他的状态码为" + conn.getResponseCode());
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

    /**
     * 封装post的参数以便传输
     * @param map 即将被封装的数据map
     * @return 封装完成后的请求体
     */
    public static String setRequestEntity(Map<String,String> map){
        String result=null;
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry: map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.deleteCharAt(sb.length()-1);
        result = sb.toString();
        return result;
    }

    public static String doHttpClientPost(String username,String password,String url){
        String result = null;
        BufferedReader bfr = null;
        List<NameValuePair> postParamters = new ArrayList<NameValuePair>();//封装请求体
        postParamters.add(new BasicNameValuePair("name", username));
        postParamters.add(new BasicNameValuePair("password",password));
        UrlEncodedFormEntity formEntity;
        try {
            formEntity = new UrlEncodedFormEntity(postParamters);//
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(formEntity);
            HttpResponse response = httpclient.execute(httpPost);
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){//如果连接成功
                Log.d("info", "连接成功，他的状态码为"+response.getStatusLine().getStatusCode());//下面就是获取服务器返回的数据了
                bfr = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer sb= new StringBuffer();
                String line = null;
                while((line=bfr.readLine())!=null){
                    sb.append(line+"\n");
                }
                result = sb.toString();
            }else{
//                Toast.makeText(MainActivity.this, "对不起，链接失败了，请检查网络", Toast.LENGTH_SHORT).show();
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(bfr!=null){
                try {
                    bfr.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        Log.d("result", result);
        return result;
    }
}

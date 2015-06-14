package com.learn.kalina.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Looper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity implements View.OnClickListener{

    String loginpath = "http://redrock.hotwoo.cn/login.php";

    private RelativeLayout mraa;
    private EditText ed_username;
    private EditText ed_password;
    private Button mlogin;
    private Button mregest;
    private TextView textView;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVIew();
        mlogin.setOnClickListener(this);
        mregest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.bt_login:
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        String username = ed_username.getText().toString();
                        String password = ed_password.getText().toString();
                        if ("".equals(username) || "".equals(password)) {
                            Looper.prepare();
                            Toast.makeText(MainActivity.this, "请填写用户名和密码", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else {
                            String result = HttpHelper.doHttpUrlConnection(username, password, loginpath);
                            if (result == null) {
                                Looper.prepare();
                                Toast.makeText(MainActivity.this, "对不起，链接失败了，请检查网络", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            } else {
                                //进行进一步解析
                                JSONTokener jsonTokener = new JSONTokener(result);
                                try {
                                    JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
                                    if (jsonObject.getString("status").equals("200")) {
                                        Looper.prepare();
                                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                        token = jsonObject.getString("data");
                                        Intent intent = new Intent(MainActivity.this, QuestionMain.class);
                                        startActivity(intent);
                                        finish();
                                        Looper.loop();
                                    } else {
                                        Looper.prepare();
                                        Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } ).start();
                break;
            case R.id.bt_regeist:
                Intent intent = new Intent(MainActivity.this, RegeistAvtivity.class);
                startActivity(intent);
                break;
        }
    }


    private String dealResponseResult(InputStream inputStream) throws Exception{
        String returnData = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        returnData = new String(outStream.toByteArray());

        return returnData;
    }

    /**
     * 封装请求体信息
     */
    private StringBuffer setRequestData(Map<String,String> params,String encode) throws Exception{
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(),encode)).append("&");
        }
        sb.deleteCharAt(sb.length()-1);//删掉最后一个&符号
        return sb;
    }

    private void initVIew() {
        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_password= (EditText) findViewById(R.id.ed_pass);
        mlogin = (Button) findViewById(R.id.bt_login);
        mregest = (Button) findViewById(R.id.bt_regeist);
        textView = (TextView) findViewById(R.id.result);
    }


    private String doHttpClientPost(String name,String password){
        BufferedReader in=null;
        try{
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(loginpath);
            //使用NameValuePair来保存要传递的Post参数
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            //添加要传递的参数
            postParameters.add(new BasicNameValuePair("name", name));
            postParameters.add(new BasicNameValuePair("password", password));
            //实例化UrlEncodedFormEntity对象
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            httpPost.setEntity(formEntity);
            HttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Log.i("aaaaaaaaaa", "连接成功，连接状态码为："+ response.getStatusLine().getStatusCode());
            }else{

            }
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer string = new StringBuffer("");
            String lineStr = "";
            while ((lineStr = in.readLine()) != null) {
                string.append(lineStr + "\n");
            }
            in.close();
            Log.d("result", string+"");
            return string.toString();
        }catch(Exception e){
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ae) {
                    e.printStackTrace();
                }
            }
        }
        return null;
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

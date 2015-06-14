package com.learn.kalina.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KalinaRain on 2015/4/28.
 */
public class RegeistAvtivity extends Activity {

    private EditText ed_username;
    private EditText ed_pass;
    private Button bt_submit;
    private String url = "http://redrock.hotwoo.cn/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_regist);

        initView();

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取两个文本框的内容
                final String reg_name = ed_username.getText().toString();
                final String reg_pass = ed_pass.getText().toString();
                if ("".equals(reg_name) || "".equals(reg_pass)) {
                    Toast.makeText(RegeistAvtivity.this, "请填写用户名和密码", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            String result = HttpHelper.doHttpUrlConnection(reg_name, reg_pass, url);
                            if (result == null) {
                                Toast.makeText(RegeistAvtivity.this, "对不起，链接失败了，请检查网络", Toast.LENGTH_SHORT).show();
                            } else {
                                //进行进一步解析
                                JSONTokener jsonTokener = new JSONTokener(result);
                                try {
                                    JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
                                    if (jsonObject.getString("status").equals("200")) {
                                        Looper.prepare();
                                        Toast.makeText(RegeistAvtivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(RegeistAvtivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Looper.loop();
                                    } else {
                                        Looper.prepare();
                                        Toast.makeText(RegeistAvtivity.this, "对不起，注册失败，请更换用户名", Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    }).start();

                }

            }
        });

    }

    private void initView() {
        ed_username = (EditText) findViewById(R.id.ed_username2);
        ed_pass = (EditText) findViewById(R.id.ed_pass2);
        bt_submit = (Button) findViewById(R.id.bt_submit);

    }

}

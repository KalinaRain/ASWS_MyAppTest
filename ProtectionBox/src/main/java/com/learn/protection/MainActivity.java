package com.learn.protection;

import android.app.ActionBar;
import android.app.Activity;
import android.app.KeyguardManager;
import android.net.http.AndroidHttpClient;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener{

    private boolean lockApp = false;//是否开启应用打开时的锁
    private int lockAppNum = 0;//密码锁定种类：0为图案锁（默认），1为数字密码锁
    private boolean lockUninstall=false;//是否开启防卸载功能
    private LockPatternView lockPatternView;
    private LockPatternUtils lockPatternUtils;
    private Button btn_set_pwd;

    private Button btn_reset_pwd;

    private Button btn_check_pwd;
    private boolean opFLag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (lockApp) {
            setContentView(R.layout.activity_main);//如果没有锁定应用，就开启原启动界面
//        } else {
//            switch (lockAppNum) {
//                case 0://图案锁
//                    setContentView(R.layout.patternlock);
//                    initLockPattern();
//                case 1://数字锁
//                    setContentView(R.layout.numberlock);
//            }
//        }

//        KeyguardManager;

        JSONArray as = new JSONArray();
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("")

        HttpClient client = new DefaultHttpClient();
//        HttpClient client = AndroidHttpClient.newInstance();
        HttpGet request = new HttpGet("http://redrock.hotwoo.cn/register.php");

//      client.execute()
//        Message
    }



    private void initLockPattern() {
        lockPatternView = (LockPatternView) findViewById(R.id.lock_pattern);
        btn_reset_pwd = (Button) findViewById(R.id.btn_reset_pwd);
        btn_set_pwd = (Button) findViewById(R.id.btn_set_pwd);
        btn_check_pwd = (Button) findViewById(R.id.btn_check_pwd);
        btn_reset_pwd.setOnClickListener(this);
        btn_set_pwd.setOnClickListener(this);
        btn_check_pwd.setOnClickListener(this);
        lockPatternUtils = new LockPatternUtils(this);
        lockPatternView.setOnPatternListener(new LockPatternView.OnPatternListener() {

            public void onPatternStart() {

            }

            public void onPatternDetected(List<LockPatternView.Cell> pattern) {
                if(opFLag){
                    int result = lockPatternUtils.checkPattern(pattern);
                    if (result!= 1) {
                        if(result==0){
                            lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                            Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_LONG).show();
                        }else{
                            lockPatternView.clearPattern();
                            Toast.makeText(MainActivity.this, "请设置密码", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "密码正确", Toast.LENGTH_LONG).show();
                    }
                }else{
                    lockPatternUtils.saveLockPattern(pattern);
                    Toast.makeText(MainActivity.this, "密码已经设置", Toast.LENGTH_LONG)
                            .show();
                    lockPatternView.clearPattern();
                }

            }

            public void onPatternCleared() {

            }

            public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

            }
        });
    }

    public void onClick(View v) {
        if (v == btn_reset_pwd) {
            lockPatternView.clearPattern();
            lockPatternUtils.clearLock();
        } else if (v == btn_check_pwd) {
            opFLag = true;
        } else {
            opFLag = false;
        }
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

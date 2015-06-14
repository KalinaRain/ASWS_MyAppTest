package com.learn.kalina.fruit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class MainActivity extends ActionBarActivity {

    private Spinner sp_color;
    private Spinner sp_shape;
    private Button bt_clear;
    private Button bt_save;
    private ArrayAdapter<CharSequence> colorAdapter;//这里不知道为什么只能是Charsequence，不可以是String
    private ArrayAdapter<CharSequence> shapeAdapter;
    private ImageView huabu;
    private Bitmap bitmap = null;
    private Canvas canvas;
    private Paint paint;
    private int paintColor;
    private int paintShape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
//        SpannableString
//        ImageSpan

        initSpinner();

        // 初始化一个画笔，笔触宽度为5，颜色为红色
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);

        MyOnTOuchLIstener mot = new MyOnTOuchLIstener();
//        huabu.setOnTouchListener(new MyOnTOuchLIstener());
        huabu.setOnTouchListener(mot);

        /**
         * 按钮--清除画板 的监听器，点击清除画板
         */
        bt_clear.setOnClickListener(new MyOnclickListener());

        /**
         * 保存按钮的监听器--点击保存已绘制的图片（可以选择保存的路径）
         */
        bt_save.setOnClickListener(new MyOnclickListener());
    }

    private void initSpinner() {
        //设置下拉列表风格
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_color.setAdapter(colorAdapter);
        sp_color.setVisibility(View.VISIBLE);//设置默认显示
        //这里只能用OnItemSelectedListener，而不是OnItemClickedListener，否则会报错
        sp_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //在此改变画笔的颜色

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        shapeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_shape.setAdapter(shapeAdapter);
        sp_shape.setVisibility(View.VISIBLE);//View的可见，并不是下拉默认不选择
        sp_shape.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //在此改变形状

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 清除内容，重绘
     */
    private void resumePicture() {
        //手动清除画板，重新创建一个画板
        if (bitmap != null) {
            //重建，即清空画板
            bitmap = Bitmap.createBitmap(huabu.getWidth(), huabu.getHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            huabu.setImageBitmap(bitmap);
            Toast.makeText(MainActivity.this, "清除画板成功，可以重新开始绘图", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 保存图片
     */
    private void savePciture() {
        try {
            //确定路径和名称，将来要做一个可以自己选择的方法
            File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".png");
            Log.d("asd", "666666666666666666666666");
            //又忘了添加权限了
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Log.d("asd", "666666666666666666661111111");
            Toast.makeText(MainActivity.this, "保存图片成功", Toast.LENGTH_SHORT).show();
            // Android设备Gallery应用只会在启动的时候扫描系统文件夹
            // 这里模拟一个媒体装载的广播，用于使保存的图片可以在Gallery中查看
            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_MEDIA_MOUNTED);//否则会报错not allowed to send broadcast android.intent.action.MEDIA_MOUNTED
            // 但是这个Intent只扫描文件，不扫描文件夹。
            // 如果你的应用只操作单个文件而不是文件夹，就可以使用这个。 2. 不发广播，换为MediaScannerConnection进行通知更新
            intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
            sendBroadcast(intent);
        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this, "保存图片失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private class MyOnTOuchLIstener implements View.OnTouchListener {

        //手指触碰屏幕的指标
        float startX;
        float startY;
//        float stopX;
//        float stopY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                //用户按下动作
                case MotionEvent.ACTION_DOWN:
                    //如果是第一次绘图，则初始化内存图片，设定背景为白色
                    Log.d("log", "------------------1");
                    if (bitmap == null) {
                        Log.d("log", "------------------2");
                        bitmap = Bitmap.createBitmap(huabu.getWidth(), huabu.getHeight(), Bitmap.Config.ARGB_8888);
                        canvas = new Canvas(bitmap);
                        canvas.drawColor(Color.WHITE);//设置背景为白色
                    }
                    Log.d("log", "------------------3");
                    //记录开始触摸的点的坐标
                    startX = event.getX();
                    startY = event.getY();
                    break;
                //用户手指在屏幕上移动的动作
                case MotionEvent.ACTION_MOVE:
                    // 记录移动位置的点的坐标
                    float stopX = event.getX();
                    float stopY = event.getY();
                    Log.d("log", "------------------4");
                    //根据两点坐标，绘制连线
                    canvas.drawLine(startX, startY, stopX, stopY, paint);
                    // 更新开始点的位置
                    startX = event.getX();
                    startY = event.getY();

                    // 把图片展示到ImageView中
                    huabu.setImageBitmap(bitmap);
                    break;
                case MotionEvent.ACTION_UP:
                    //这里好像不需要
                    break;
                default:

                    break;
            }

            return true;//!!!!!!!!!!!!!
        }
    }

    /**
     * 将两个按钮的点击事件整合起来！！！
     */
    private class MyOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_clear:
                    resumePicture();
                    break;
                case R.id.bt_save:
                    savePciture();
                    break;
            }
        }
    }

    private void initView() {
        sp_color = (Spinner) findViewById(R.id.select_color);
        sp_shape = (Spinner) findViewById(R.id.select_shape);
        bt_clear = (Button) findViewById(R.id.bt_clear);
        bt_save = (Button) findViewById(R.id.bt_save);
        colorAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.color_select, android.R.layout.simple_list_item_1);
        shapeAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.shape_select, android.R.layout.simple_list_item_1);
        huabu = (ImageView) findViewById(R.id.huabu);
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

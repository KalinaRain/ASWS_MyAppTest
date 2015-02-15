package com.learn.kalina.sensor;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Button bt_query;
    private TextView tv_sensor_result;
    private SensorManager sensorManager;
    private TextView name;
    private List<Sensor> allSensors;
    private Sensor sensor;
    private StringBuilder stringBuilder;
    private boolean hasClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onViewInit();
        onInit();
    }

    private void onInit() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);


        bt_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasClicked) {//如果已经点击（查询）了，则将textview内容清除
                    tv_sensor_result.setText("");
                    stringBuilder = new StringBuilder();//清空内容
                    hasClicked = false;
                } else {//如果尚未查询则显示传感器的具体信息
                    stringBuilder = new StringBuilder();
                    startQuery();
                    tv_sensor_result.setText(stringBuilder.toString());
                    hasClicked = true;
                }

            }
        });
    }

    /**
        query the sensors that the mobile have
     */
    private void startQuery() {
        stringBuilder.append("该手机一个有" + allSensors.size() + "个传感器，分别为：" + "\n");
        for (int i = 0; i < allSensors.size(); i++) {
            sensor = allSensors.get(i);
            stringBuilder.append("设备名称 " + sensor.getName() + "\n");
            stringBuilder.append("设备版本 " + sensor.getVersion() + "\n");
            stringBuilder.append("通用类型号 " + sensor.getType() + "\n");
            stringBuilder.append("设备商名称 " + sensor.getVendor() + "\n");
            stringBuilder.append("传感器功耗 " + sensor.getPower() + "\n");
            stringBuilder.append("传感器分辨率 " + sensor.getResolution() + "\n");
            stringBuilder.append("传感器最大量程 " + sensor.getMaximumRange() + "\n");
            switch (sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    stringBuilder.append("第" + (i+1) + ":加速度传感器");
                    break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE://不再使用TYPE_TEMPERATURE
                    stringBuilder.append("第" + (i+1) + ":温度传感器");
                    break;
                case Sensor.TYPE_GAME_ROTATION_VECTOR:
                    stringBuilder.append("第" + (i+1) + ":游戏旋转矢量传感器");
                    break;
                case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                    stringBuilder.append("第" + (i+1) + ":地磁旋转矢量传感器");
                    break;
                case Sensor.TYPE_GRAVITY:
                    stringBuilder.append("第" + (i+1) + ":重力传感器");
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    stringBuilder.append("第" + (i+1) + ":陀螺仪传感器");
                    break;
                case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                    stringBuilder.append("第" + (i+1) + ":未校准陀螺仪传感器");
                    break;
                case Sensor.TYPE_LIGHT:
                    stringBuilder.append("第" + (i+1) + ":环境光线传感器");
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    stringBuilder.append("第" + (i+1) + ":线性加速度传感器");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    stringBuilder.append("第" + (i+1) + ":磁场传感器");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                    stringBuilder.append("第" + (i+1) + ":未校准磁场传感器");
                    break;
                case Sensor.TYPE_ORIENTATION:
                    //use SensorManager.getOrientation() instead.
                    stringBuilder.append("第" + (i+1) + ":方向传感器");
                    break;
                case Sensor.TYPE_PRESSURE:
                    stringBuilder.append("第" + (i+1) + ":压力传感器");
                    break;
                case Sensor.TYPE_PROXIMITY:
                    stringBuilder.append("第" + (i+1) + ":距离传感器（近距离感应）");
                    break;
                case Sensor.TYPE_RELATIVE_HUMIDITY:
                    stringBuilder.append("第" + (i+1) + ":湿度传感器");
                    break;
                case Sensor.TYPE_ROTATION_VECTOR:
                    stringBuilder.append("第" + (i+1) + ":旋转矢量传感器");
                    break;
                case Sensor.TYPE_SIGNIFICANT_MOTION:
                    stringBuilder.append("第" + (i+1) + ":显著运动传感器");
                    break;
                case Sensor.TYPE_STEP_COUNTER:
                    stringBuilder.append("第" + (i+1) + ":step counter传感器（可能是计数传感器）");
                    break;
                case Sensor.TYPE_STEP_DETECTOR:
                    stringBuilder.append("第" + (i+1) + ":step detector传感器（可能是用来检测计数的）");
                    break;
                default:
                    stringBuilder.append("第" + (i+1) + ":未知传感器");
                    break;
            }
            stringBuilder.append("\n" + "--------------------------------------------------" + "\n");
        }
    }

    private void onViewInit() {
        bt_query = (Button) findViewById(R.id.bt_query);
        tv_sensor_result = (TextView) findViewById(R.id.tv_query_result);

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

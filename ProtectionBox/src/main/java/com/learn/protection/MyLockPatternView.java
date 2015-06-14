package com.learn.protection;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by KalinaRain on 2015/5/1.
 */
public class MyLockPatternView extends View {

    public Point[][] point = new Point[3][3];//九个点
    public boolean isInit = false;//点是否初始化过

    public MyLockPatternView(Context context) {
        super(context);
    }

    public MyLockPatternView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLockPatternView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 自定义的点
     */
    public static class Point {
        //每个点有三种状态
        public static int STATE_NOMAL = 0;//正常状态下

        public static int STATE_PRESSED = 1;//用户按下去的时候的状态

        public static int STATE_EOORO = 2;//图案绘制错误的时候的状态

        public static float x,y;


    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            initPoints();//如果没有进行初始化，就把点进行初始化
        }
        startDrawPoint(canvas);//如果已经初始化过了，就开始绘制点
    }

    /**
     * 初始化9个点
     */
    private void initPoints() {
        getHeight();
        getWidth();
    }

    /**
     *
     */
    private void startDrawPoint(Canvas canvas) {

    }

}

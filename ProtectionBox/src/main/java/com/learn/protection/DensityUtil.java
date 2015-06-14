package com.learn.protection;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by KalinaRain on 2015/4/12.
 */
public class DensityUtil {
    private static final String TAG = DensityUtil.class.getSimpleName();

    private static DisplayMetrics dm;
    private static float dmDensity = 0.0f;// 当前屏幕的densityDpi
    private static float dmDensityDpi = 0.0f;// 当前屏幕的densityDpi
    private static float scale = 0.0f;//也就是dm.density

    /**
     * 根据构造函数获得当前手机的屏幕系数
     */
    public DensityUtil(Context context) {
        // 获取当前屏幕
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        // 设置Density
        setDmDensity(dm.density);
        // 密度因子
        scale = getDmDensity();//density = dm.densityDpi/160
        dmDensityDpi = dm.densityDpi;//每英寸含有的像素数
        Log.i(TAG, toString());
    }

    /**
     * 获取当前屏幕的density
     */
    public static float getDmDensity() {
        return dmDensity;
    }

    /**
     * 设置当前屏幕的density
     */
    public static void setDmDensity(float dmDensity) {
        DensityUtil.dmDensity = dmDensity;
    }

    /**
     * dpi转换像素
     */
    public static int dip2px(float dipValue) {
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 像素转换dpi
     * */
    public int px2dip(float pxValue) {
        return (int) (pxValue / scale + 0.5f);
    }

    @Override
    public String toString() {
        return " dmDensity:" + dmDensity + " dmDensityDpi:" + dmDensityDpi;
    }
}

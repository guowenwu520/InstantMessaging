package com.cd.myluntan.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

import com.cd.myluntan.R;

public class WindowUitls {
    //设置透明状态栏
    public  static  void  setTransparentTopBar(Activity context){
        if(Build.VERSION.SDK_INT >= 21){
            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            context.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //设置状态栏颜色
    public  static  void  setColorTopBar(Activity context,int colorid){
        context.getWindow().setStatusBarColor(context.getResources().getColor(colorid));
    }

    //设置状态栏字体颜色
    public  static  void  setColorTextTopBarBlack(Activity context){
        context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
    //设置状态栏字体颜色
    public  static  void  setColorTextTopBarWriter(Activity context){
        context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
    }
}

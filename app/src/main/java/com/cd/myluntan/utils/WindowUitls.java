package com.cd.myluntan.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.cd.myluntan.R;
import com.cd.myluntan.ui.activity.Dynamic_Details_Activity;
import com.cd.myluntan.ui.customui.Picker;

import static android.content.Context.INPUT_METHOD_SERVICE;

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
    //打开软键盘
    public  static  void setOpenInput(EditText editText, Context context){
        //打开软件盘
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
    //弹出d底部系统框
    public static void ShowBottomBarSelect(Activity context,String [] wuran_headers,View view) {
        Picker picker_wuran_Picker = new Picker(context,view, wuran_headers);
        picker_wuran_Picker.setOnSelectDoneListener(new Picker.OnSelectDoneListener() {
            @Override
            public void onSelectDone(int i) {
                Toast.makeText(context, wuran_headers[i], Toast.LENGTH_SHORT).show();
            }
        });
        picker_wuran_Picker.show();
    }
}

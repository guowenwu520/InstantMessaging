package com.cd.myluntan.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.interfaceo.BottomUpdateCallback;
import com.cd.myluntan.interfaceo.NetworkCallback;
import com.cd.myluntan.ui.customui.Picker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.DELETEDYNAMIC;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.GETUSERBYID;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

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
    public static void ShowBottomBarSelect(Activity context, String[] wuran_headers, View view, Dynamic dynamic, BottomUpdateCallback key) {
        Picker picker_wuran_Picker = new Picker(context,view, wuran_headers);
        picker_wuran_Picker.setOnSelectDoneListener(new Picker.OnSelectDoneListener() {
            @Override
            public void onSelectDone(int i) {
               switch (wuran_headers[i]){
                   case "删除":
                       deleteDyniamic(dynamic,key);
                       break;
                   case "收藏":
                       break;
                   case "举报":
                       break;
                   case "转发":
                       break;
                   case "分享图片":
                       break;
               }
            }
        });
        picker_wuran_Picker.show();
    }
    //弹出d底部系统框
    public static void ShowBottomBarSelect(Activity context, String[] wuran_headers, View view,  BottomUpdateCallback key) {
        Picker picker_wuran_Picker = new Picker(context,view, wuran_headers);
        picker_wuran_Picker.setOnSelectDoneListener(new Picker.OnSelectDoneListener() {
            @Override
            public void onSelectDone(int i) {
                switch (wuran_headers[i]){
                    case "录制":
                      key.bottomBarShow(333);
                        break;
                    case "本地视频":
                        key.bottomBarShow(222);
                        break;
                }
            }
        });
        picker_wuran_Picker.show();
    }
    private static void deleteDyniamic(Dynamic dynamic, BottomUpdateCallback key) {
        Map<String,String> map=new HashMap<>();
        map.put("id",dynamic.getId());
        Data_Access.AccessStringDate(URL + DELETEDYNAMIC, map, new NetworkCallback() {
            @Override
            public Object parseNetworkResponse(Response response) {
                key.bottomBarShow(0);
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }
}

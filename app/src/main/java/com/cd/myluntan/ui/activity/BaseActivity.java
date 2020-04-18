package com.cd.myluntan.ui.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

public class BaseActivity extends AppCompatActivity {
    /**
     * 隐藏通知栏和虚拟按键
     *
     * @param decorView 顶层视图
     */
    public void hideNavigationBar(View decorView) {
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        //判断当前版本在4.0以上并且存在虚拟按键，否则不做操作
        if (Build.VERSION.SDK_INT < 19 || !checkDeviceHasNavigationBar()) {
            //一定要判断是否存在按键，否则在没有按键的手机调用会影响别的功能。如之前没有考虑到，导致图传全屏变成小屏显示。
            return;
        } else {
            // 获取属性
            decorView.setSystemUiVisibility(flag);
        }
    }

    /**
     * 判断是否存在虚拟按键
     *
     * @return false:不存在虚拟按键   true:存在虚拟按键
     */
    private boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    /**
     * 隐藏软件盘
     */
    public void hidSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        }
    }

    /**
     * 申请相关权限
     *
     * @param permission 相关权限
     */
    public void applyPermission(String permission) {
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add(permission);
        applyPermission(permissions);
    }

    /**
     * 申请相关权限
     *
     * @param permissions 相关权限的集合
     */
    public void applyPermission(ArrayList<String> permissions) {
        ArrayList<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.size(); i++) {
            String permission = permissions.get(i);
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                //权限未被申请，添加到未被申请的列表中
                Log.d("1111", "权限未被申请，添加到未被申请的列表中" + permission);
                permissionList.add(permission);
            }
        }
        String[] strings = new String[permissionList.size()];
        for (int i = 0; i < permissionList.size(); i++) {
            strings[i] = permissionList.get(i);
            Log.d("1111", "权限未被申请，添加到未被申请的列表中1" + i);

        }
        ActivityCompat.requestPermissions(this, strings, 100);
    }

    /**
     * 检查是否有相关权限
     *
     * @param permission 相关权限
     * @return true：权限已开启 false：权限未开启
     */
    public Boolean hasPermission(String permission) {
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add(permission);
        return hasPermission(permissions);
    }

    /**
     * 检查是否有相关权限
     *
     * @param permissions 相关权限的集合
     * @return true：权限已开启 false：权限未开启
     */
    public Boolean hasPermission(ArrayList<String> permissions) {
        boolean result = true;
        for (String permission : permissions) {
            result = ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return result;
    }
}

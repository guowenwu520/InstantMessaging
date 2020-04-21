package com.cd.myluntan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cd.myluntan.R;
import com.cd.myluntan.contract.SplashContract;
import com.cd.myluntan.presenter.SplashPresenter;

public class SplashActivity extends BaseActivity implements SplashContract.View {
    private static final String TAG =SplashActivity.class.getCanonicalName();
    private TextView jumpOver;

    private SplashPresenter splashPresenter;

    Handler handler = new Handler();
    Runnable runnable = () -> {
//        Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
//        startActivity(intent);
        splashPresenter=new SplashPresenter(this,SplashActivity.this);
        splashPresenter.checkLoginStatus();
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        jumpOver = findViewById(R.id.jumpOver);
        handler.postDelayed(runnable, 100);//3秒后执行Runnable中的run方法
        initJumpOver();
    }

    private void initJumpOver() {
        jumpOver.setOnClickListener(v -> {
            handler.removeCallbacks(runnable);
            Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onNotLogin() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"onNotLogin");
                Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onLogin() {
        Log.d(TAG,"onLogin");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
        Intent intent=new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
            }
        });
    }
}

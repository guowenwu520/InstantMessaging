package com.cd.myluntan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cd.myluntan.R;
import com.cd.myluntan.contract.SplashContract;
import com.cd.myluntan.presenter.SplashPresenter;

public class SplashActivity extends BaseActivity implements SplashContract.View {
    private TextView jumpOver;

    private SplashPresenter splashPresenter;

    Handler handler = new Handler();
    Runnable runnable = () -> {
        Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashPresenter=new SplashPresenter(this);
        splashPresenter.checkLoginStatus();
        setContentView(R.layout.activity_splash);
        jumpOver = findViewById(R.id.jumpOver);
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
        handler.postDelayed(runnable, 3000);//3秒后执行Runnable中的run方法
    }

    @Override
    public void onLogin() {
        Intent intent=new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

package com.cd.myluntan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cd.myluntan.R;
import com.cd.myluntan.contract.RegisterContract;
import com.cd.myluntan.presenter.RegisterPresenter;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, RegisterContract.View {
    private LinearLayout outOfRange;
    private EditText username, password, rePassword;
    private TextInputLayout inputUsername, inputPassword, inputRePassword;
    private TextView login, register;

    private Handler handler = new Handler();
    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerPresenter = new RegisterPresenter(this,this);
        initView();
    }

    private void initView() {
        outOfRange = findViewById(R.id.outOfRange);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.rePassword);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        inputRePassword = findViewById(R.id.inputRePassword);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        outOfRange.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        editTextDrawable(username,0,20,20);
        editTextDrawable(password,0,20,20);
        editTextDrawable(rePassword,0,20,20);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.outOfRange:
                hidSoftKeyboard();
                break;
            case R.id.login:
                hidSoftKeyboard();
                finish();
                break;
            case R.id.register:
                hidSoftKeyboard();
                register.setEnabled(false);
                register.setText(R.string.registering);
                registerPresenter.onRegister(username.getText().toString(),password.getText().toString(),rePassword.getText().toString());
                break;
        }
    }

    @Override
    public void onRegisterSuccess() {
        runOnUiThread(() -> {
            register.setText(R.string.register_success);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);//3秒后执行Runnable中的run方法
        });
    }

    @Override
    public void onRegisterFailed(int error) {
        runOnUiThread(() -> {
            register.setEnabled(true);
            register.setText(getText(R.string.register));
            Toast.makeText(this, getText(error), Toast.LENGTH_SHORT).show();
        });
    }
}

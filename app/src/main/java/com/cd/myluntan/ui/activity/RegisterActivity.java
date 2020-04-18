package com.cd.myluntan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cd.myluntan.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText username, password, rePassword;
    private TextInputLayout inputUsername, inputPassword, inputRePassword;
    private TextView login, register;

    //    private RegisterPresenter registerPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.rePassword);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        inputRePassword = findViewById(R.id.inputRePassword);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                finish();
                break;
            case R.id.register:

                break;
        }
    }
}

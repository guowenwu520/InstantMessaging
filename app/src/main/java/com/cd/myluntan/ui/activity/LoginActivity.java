package com.cd.myluntan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.TextWatcherAdapter;
import com.cd.myluntan.contract.LoginContract;
import com.cd.myluntan.presenter.LoginPresenter;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContract.View {
    private EditText username, password;
    private TextInputLayout inputUsername, inputPassword;
    private TextView login, register, recoverPassword;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this, this);
        initView();
    }

    private void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        recoverPassword = findViewById(R.id.recoverPassword);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        recoverPassword.setOnClickListener(this);
        initUsername();
        initPassword();
    }

    private void initPassword() {
        password.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                if (inputPassword.getCounterMaxLength() < password.length()) {
                    inputPassword.setError("超出字数限制！");
                } else {
                    inputPassword.setErrorEnabled(false);
                }
            }
        });
    }

    private void initUsername() {
        username.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                if (inputUsername.getCounterMaxLength() < username.length()) {
                    inputUsername.setError("超出字数限制！");
                } else {
                    inputUsername.setErrorEnabled(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                hidSoftKeyboard();
                login.setText(R.string.logging_in);
                login.setEnabled(false);
                loginPresenter.login(username.getText().toString(), password.getText().toString());
                break;
            case R.id.recoverPassword:
                hidSoftKeyboard();
                break;
            case R.id.register:
                hidSoftKeyboard();
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onLoginSuccess() {
        runOnUiThread(() -> {
            //登录成功跳转页面
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    @Override
    public void onLoginFailed(int error) {
        runOnUiThread(() -> {
            //登录失败
            Toast.makeText(this, getText(error), Toast.LENGTH_SHORT).show();
            login.setText(R.string.login);
            login.setEnabled(true);
        });
    }
}

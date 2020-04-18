package com.cd.myluntan.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText username, password;
    private TextInputLayout inputUsername, inputPassword;
    private TextView login, register, recoverPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                break;
            case R.id.recoverPassword:
                break;
            case R.id.register:
                break;
        }
    }
}

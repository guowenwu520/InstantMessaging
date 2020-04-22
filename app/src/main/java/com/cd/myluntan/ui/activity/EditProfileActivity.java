package com.cd.myluntan.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cd.myluntan.R;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener {
    private ImageView avatar;
    private LinearLayout nicknameLL, sexLL, birthdayLL, signatureLL, passwordLL;
    private TextView nickname, sex, birthday, signature, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initView();
    }

    private void initView() {
        avatar = findViewById(R.id.avatar);
        nicknameLL = findViewById(R.id.nicknameLL);
        sexLL = findViewById(R.id.sexLL);
        birthdayLL = findViewById(R.id.birthdayLL);
        signatureLL = findViewById(R.id.signatureLL);
        passwordLL = findViewById(R.id.passwordLL);
        nickname = findViewById(R.id.nickname);
        sex = findViewById(R.id.sex);
        birthday = findViewById(R.id.birthday);
        signature = findViewById(R.id.signature);
        password = findViewById(R.id.password);
        avatar.setOnClickListener(this);
        nicknameLL.setOnClickListener(this);
        sexLL.setOnClickListener(this);
        birthdayLL.setOnClickListener(this);
        signatureLL.setOnClickListener(this);
        passwordLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatar:
                Toast.makeText(this, "avatar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nicknameLL:
                Toast.makeText(this, "nicknameLL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sexLL:
                Toast.makeText(this, "sexLL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.birthdayLL:
                Toast.makeText(this, "birthdayLL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.signatureLL:
                Toast.makeText(this, "signatureLL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.passwordLL:
                Toast.makeText(this, "passwordLL", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

package com.cd.myluntan.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.cd.myluntan.R;

public class ChatActivity extends BaseActivity {

    public static ChatActivity activityInstance;
    String toChatUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
}

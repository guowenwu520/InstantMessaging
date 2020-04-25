package com.cd.myluntan.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.cd.myluntan.R;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.ui.fragment.ChatFragment;
import com.cd.myluntan.utils.Singletion;
import com.cd.myluntan.utils.WindowUitls;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class ChatActivity extends BaseActivity {

    public static ChatActivity activityInstance;
    String toChatUsername;
    User user;
    User myUser;
    private EaseChatFragment chatFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        WindowUitls.setColorTopBar(this,R.color.white);
        WindowUitls.setColorTextTopBarBlack(this);
        user= Singletion.getInstance().getOtherUser();
        myUser=Singletion.getInstance().getUser();
        activityInstance = this;
        //get user id or group id
        toChatUsername = user.getName();
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }
}

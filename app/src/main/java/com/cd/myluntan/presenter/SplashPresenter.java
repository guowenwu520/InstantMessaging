package com.cd.myluntan.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.cd.myluntan.adapter.EMCallBackAdapter;
import com.cd.myluntan.contract.SplashContract;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.ui.activity.LoginActivity;
import com.cd.myluntan.ui.activity.MainActivity;
import com.cd.myluntan.ui.activity.SplashActivity;
import com.cd.myluntan.utils.Singletion;
import com.hyphenate.chat.EMClient;

public class SplashPresenter implements SplashContract.Presenter {
    private SplashContract.View view;
   private  Context context;
    public SplashPresenter(Context splashActivity, SplashContract.View view) {
        this.view = view;
        context=splashActivity;
    }

    @Override
    public void checkLoginStatus() {
       isLoggedIn();
    }

    /**
     * 检查是否一登陆设备
     * @return
     */
    private void isLoggedIn() {
        SharedPreferences sharedPreferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
         String name=sharedPreferences.getString("name","12");
         String pass=sharedPreferences.getString("pass","12");
         loginModel(name,pass);
     //   return EMClient.getInstance().isConnected() && EMClient.getInstance().isLoggedInBefore();
    }

    private void loginModel(String username, String password) {
        EMClient.getInstance().login(username, password, new EMCallBackAdapter() {//回调
            @Override
            public void onSuccess() {
//                本地会话和群组都 load 完毕
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                //记录本地账号
                savePass_Name(username, password);
                view.onLogin();
            }

            @Override
            public void onError(int code, String message) {
                view.onNotLogin();
            }
        });
    }
    //用户
    private void savePass_Name(String username, String password) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("name",username);
        editor.putString("pass",password);
        editor.commit();
        User user=new User();
        user.setName(username);
        user.setPass(password);
        user.setId(System.currentTimeMillis()+"");
        Singletion.getInstance().setUser(user);
    }
}

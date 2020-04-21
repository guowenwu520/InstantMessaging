package com.cd.myluntan.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.cd.myluntan.adapter.EMCallBackAdapter;
import com.cd.myluntan.contract.LoginContract;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.utils.Singletion;
import com.hyphenate.chat.EMClient;

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {
    private static final String TAG = LoginPresenter.class.getCanonicalName();
    private Context context;
    private LoginContract.View view;

    public LoginPresenter(Context context, LoginContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void login(String username, String password) {
        if (!username.isEmpty() && !password.isEmpty()) {
            loginModel(username, password);

        } else {
            view.onLoginFailed(error(0));
        }
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
                Log.d(TAG, "登录聊天服务器成功！");
                view.onLoginSuccess();
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "登录失败==>code:" + code + "===>message:" + message + "=======" + context.getString(error(code)));
                view.onLoginFailed(error(code));
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

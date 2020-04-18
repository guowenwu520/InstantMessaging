package com.cd.myluntan.presenter;

import android.content.Context;
import android.util.Log;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.EMCallBackAdapter;
import com.cd.myluntan.contract.LoginContract;
import com.hyphenate.chat.EMClient;

public class LoginPresenter implements LoginContract.Presenter {
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

    private int error(int code) {
        if (code == 304) {//304==无法访问服务器
            return R.string.can_not_get_server;
        } else if (code == 204) {//204== 用户不存在
            return R.string.user_not_exist;
        } else if (code == 202) {//202 用户名或密码错误
            return R.string.username_or_password_is_wrong;
        } else return R.string.username_or_password_is_null;
    }
}

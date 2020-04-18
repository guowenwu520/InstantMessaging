package com.cd.myluntan.presenter;

import android.content.Context;
import android.util.Log;

import com.cd.myluntan.R;
import com.cd.myluntan.contract.RegisterContract;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;


public class RegisterPresenter extends BasePresenter implements RegisterContract.Presenter {
    private static final String TAG =RegisterPresenter.class.getCanonicalName();
    private Context context;
    private RegisterContract.View view;

    public RegisterPresenter(Context context, RegisterContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onRegister(String username, String password,String rePassword) {
        if (!username.isEmpty()&&!password.isEmpty()){
            if (password.equals(rePassword)){
                registerModel(username, password);
            }else {
                view.onRegisterFailed(R.string.password_are_different);
            }
        }else view.onRegisterFailed(R.string.username_or_password_is_null);
    }

    private void registerModel(String username, String password) {
        new Thread(() -> {
            //注册失败会抛出HyphenateException
            try {
                EMClient.getInstance().createAccount(username, password);//同步方法
                view.onRegisterSuccess();
            } catch (HyphenateException e) {
                Log.d(TAG,"registerModel==="+e.toString()+"===="+e.getErrorCode());
                view.onRegisterFailed(error(e.getErrorCode()));
            }
        }).start();
    }
}

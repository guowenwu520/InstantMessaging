package com.cd.myluntan.presenter;

import android.content.Context;
import android.util.Log;

import com.cd.myluntan.R;
import com.cd.myluntan.contract.RegisterContract;
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.interfaceo.NetworkCallback;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import okhttp3.Call;
import okhttp3.Response;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.ADDUSER;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;


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
                User user=new User();
                user.setName(username);
                user.setPass(password);
                user.setId(System.currentTimeMillis()+"");
                Data_Access.AccessJSONDate(URL + ADDUSER, new Gson().toJson(user), new NetworkCallback() {
                    @Override
                    public Object parseNetworkResponse(Response response) {
                        view.onRegisterSuccess();
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                          e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Object response) {

                    }
                });
            } catch (HyphenateException e) {
                Log.d(TAG,"registerModel==="+e.toString()+"===="+e.getErrorCode());
                view.onRegisterFailed(error(e.getErrorCode()));
            }
        }).start();
    }
}

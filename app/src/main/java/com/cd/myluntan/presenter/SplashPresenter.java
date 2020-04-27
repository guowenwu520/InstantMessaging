package com.cd.myluntan.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.cd.myluntan.adapter.EMCallBackAdapter;
import com.cd.myluntan.contract.SplashContract;
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.interfaceo.NetworkCallback;
import com.cd.myluntan.ui.activity.LoginActivity;
import com.cd.myluntan.ui.activity.MainActivity;
import com.cd.myluntan.ui.activity.SplashActivity;
import com.cd.myluntan.utils.Singletion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.GETUSERBYNAME;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

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
         String name=sharedPreferences.getString("name","");
         String pass=sharedPreferences.getString("pass","");
         if (!name.isEmpty()&&!pass.isEmpty()){
             loginModel(name,pass);
         }else {
             view.onNotLogin();
         }
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
        Map<String,String> map=new HashMap<>();
        map.put("pass",password);
        map.put("name",username);
        Data_Access.AccessStringDate(URL + GETUSERBYNAME, map, new NetworkCallback() {
            @Override
            public Object parseNetworkResponse(Response response) {
                TypeToken<User> userTypeToken=new TypeToken<User>(){};
                Gson gson=new Gson();
                User user= null;
                try {
                    user = gson.fromJson(response.body().string(),userTypeToken.getType());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Singletion.getInstance().setUser(user);
                view.onLogin();
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }
}

package com.cd.myluntan.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.cd.myluntan.adapter.EMCallBackAdapter;
import com.cd.myluntan.contract.LoginContract;
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.interfaceo.NetworkCallback;
import com.cd.myluntan.utils.Singletion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.ADDUSER;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.DELETEPRAISE;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.GETUSERBYNAME;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

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
            }

            @Override
            public void onError(int code, String message) {
                view.onLoginFailed(error(code));
            }
        });
    }

    //用户
    private void savePass_Name(String username, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", username);
        editor.putString("pass", password);
        editor.commit();
        Map<String, String> map = new HashMap<>();
        map.put("pass", password);
        map.put("name", username);
        Data_Access.AccessStringDate(URL + GETUSERBYNAME, map, new NetworkCallback() {
            @Override
            public Object parseNetworkResponse(Response response) {
                TypeToken<User> userTypeToken = new TypeToken<User>() {
                };
                Gson gson = new Gson();
                User user = null;
                try {
                    user = gson.fromJson(response.body().string(), userTypeToken.getType());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Singletion.getInstance().setUser(user);
                view.onLoginSuccess();
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {
                view.onLoginFailed(error(e.hashCode()));
            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }
}

package com.cd.myluntan.contract;

/**
 * 登录MVP协议
 */
public interface LoginContract {
    interface Model {

    }

    interface View {
        void onLoginSuccess();
        void onLoginFailed(int error);
    }

    interface Presenter {
        void login(String username, String password);
    }
}

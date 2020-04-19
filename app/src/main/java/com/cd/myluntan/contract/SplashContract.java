package com.cd.myluntan.contract;

public interface SplashContract {
    interface View{
        void onNotLogin();//没有登录
        void onLogin();//已经登录
    }
    interface Presenter{
        void checkLoginStatus();//检查登陆状态
    }
}

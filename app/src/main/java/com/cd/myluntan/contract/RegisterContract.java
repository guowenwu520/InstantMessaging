package com.cd.myluntan.contract;

/**
 * 注册功能MVP协议
 */
public interface RegisterContract {
    interface Model{

    }
    interface View{
        void onRegisterSuccess();
        void onRegisterFailed(int error);
    }
    interface Presenter{
        void onRegister(String username,String password,String rePassword);
    }
}

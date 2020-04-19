package com.cd.myluntan.presenter;

import com.cd.myluntan.contract.SplashContract;
import com.hyphenate.chat.EMClient;

public class SplashPresenter implements SplashContract.Presenter {
    private SplashContract.View view;

    public SplashPresenter(SplashContract.View view) {
        this.view = view;
    }

    @Override
    public void checkLoginStatus() {
        if (isLoggedIn()) {
            view.onLogin();
        } else {
            view.onNotLogin();
        }
    }

    /**
     * 检查是否一登陆设备
     * @return
     */
    private boolean isLoggedIn() {
        return EMClient.getInstance().isConnected() && EMClient.getInstance().isLoggedInBefore();
    }
}

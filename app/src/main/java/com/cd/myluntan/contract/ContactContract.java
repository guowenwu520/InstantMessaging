package com.cd.myluntan.contract;

import java.util.ArrayList;

/**
 * 粉丝和关注MVP协议
 */
public interface ContactContract {
    interface Model{
        ArrayList<String> getAttention();
        ArrayList<String> getFan();
    }
    interface View{
        void onLoadContactSuccess(int type);
        void onLoadContactFailed(String err);
    }
    interface Presenter{
        void loadAttention();
        void loadFan();

        void onRefresh();
    }
}

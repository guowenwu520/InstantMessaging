package com.cd.myluntan.contract;

public interface MainContract {
    interface Model{

    }
    interface View{
        void bottomBarShow(int dy);
    }
    interface Presenter{
        void bottomBarShow(int dy);
    }
}

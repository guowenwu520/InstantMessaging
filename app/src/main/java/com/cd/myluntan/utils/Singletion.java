package com.cd.myluntan.utils;

import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.User;

public class Singletion {
     private   User user=new User();
    private Dynamic dynamic;
    //top指示
    private int index=0;
    //数据位置
    private  int postion=0;
    private  static   Singletion singletion;

    private Singletion() {
        user.setId("23232");
        user.setName("232323");
        user.setHead_url("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1245886665,169279313&fm=26&gp=0.jpg");
        user.setSignaturnre("天天开心");
    }
    public  static  Singletion getInstance(){
        if(singletion==null){
            singletion=new Singletion();
        }
        return  singletion;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dynamic getDynamic() {
        return dynamic;
    }

    public void setDynamic(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }
}

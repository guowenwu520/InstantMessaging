package com.cd.myluntan.utils;

import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Imgs;
import com.cd.myluntan.entrty.User;

import java.util.ArrayList;

public class Singletion {
    //自己
     private   User user=new User();
     //英语跳转
    private  User otherUser;
    private Dynamic dynamic;
    //粉丝
    private ArrayList<User> follow;
    //top指示
    private int index=0;
    //数据位置
    private  int postion=0;
    private  ArrayList<Imgs> imgs;
    private  static   Singletion singletion;
    private Singletion() {
    }

    public ArrayList<Imgs> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<Imgs> imgs) {
        this.imgs = imgs;
    }

    public  static  Singletion getInstance(){
        if(singletion==null){
            singletion=new Singletion();
        }
        return  singletion;
    }

    public User getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(User otherUser) {
        this.otherUser = otherUser;
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

    public ArrayList<User> getFollow() {
        return follow;
    }

    public void setFollow(ArrayList<User> follow) {
        this.follow = follow;
    }
}

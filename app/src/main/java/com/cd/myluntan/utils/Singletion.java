package com.cd.myluntan.utils;

import com.cd.myluntan.entrty.User;

public class Singletion {
     private   User user=new User();
     private  static   Singletion singletion;

    private Singletion() {
        user.setId("23232");
        user.setName("232323");
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
}

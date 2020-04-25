package com.cd.myluntan.entrty;

/**
 * Created by 18179 on 2020/2/4.
 */

public class Praise {
   String id;
   String dynamicid;
   User user;
  String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDynamicid() {
        return dynamicid;
    }

    public void setDynamicid(String dynamicid) {
        this.dynamicid = dynamicid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Praise{" +
                "id='" + id + '\'' +
                ", dynamicid='" + dynamicid + '\'' +
                ", user=" + user +
                ", userid='" + userid + '\'' +
                '}';
    }
}

package com.cd.myluntan.entrty;

import java.util.ArrayList;

/**
 * Created by 18179 on 2020/2/4.
 */

public class Comment {
   String id;
   String dynamicid;
    String commitmag;
    String committime;
   //评论者
   User user;
   //被评论者
    User usered;
   //回复或者评论
   String type;
   //评论的回复
    ArrayList<Comment> comments;
    String userid;
    String useredid;

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String getUserid() {
        return userid;
    }

    public String getDynamicid() {
        return dynamicid;
    }

    public void setDynamicid(String dynamicid) {
        this.dynamicid = dynamicid;
    }

    public String getCommitmag() {
        return commitmag;
    }

    public void setCommitmag(String commitmag) {
        this.commitmag = commitmag;
    }

    public String getCommittime() {
        return committime;
    }

    public void setCommittime(String committime) {
        this.committime = committime;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUseredid() {
        return useredid;
    }

    public void setUseredid(String useredid) {
        this.useredid = useredid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUsered() {
        return usered;
    }

    public void setUsered(User usered) {
        this.usered = usered;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", dynamicid='" + dynamicid + '\'' +
                ", commitmag='" + commitmag + '\'' +
                ", committime='" + committime + '\'' +
                ", user=" + user +
                ", usered=" + usered +
                ", type='" + type + '\'' +
                ", comments=" + comments +
                ", userid='" + userid + '\'' +
                ", useredid='" + useredid + '\'' +
                '}';
    }
}

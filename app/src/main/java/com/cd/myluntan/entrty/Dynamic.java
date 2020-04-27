package com.cd.myluntan.entrty;

import java.util.ArrayList;

/**
 * Created by 18179 on 2020/2/4.
 */

public class Dynamic {
    String id;
    //发布时间
    String time;
    //发布用户
    User user;
    //发布文字
    String mag;
    //发布图片;
    ArrayList<Imgs> imgs;
    //评论
    ArrayList<Comment> comments;
    //点赞
    ArrayList<Praise> praises;
  //所属标签
    ArrayList<Label> labels;
   //动态类型
    String type;

    String userid;
    String classs;

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public ArrayList<Imgs> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<Imgs> imgs) {
        this.imgs = imgs;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }

    public String getType() {
        return type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<Label> labels) {
        this.labels = labels;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<Praise> getPraises() {
        return praises;
    }

    public void setPraises(ArrayList<Praise> praises) {
        this.praises = praises;
    }

    @Override
    public String toString() {
        return "Dynamic{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", user=" + user +
                ", mag='" + mag + '\'' +
                ", imgs=" + imgs +
                ", comments=" + comments +
                ", praises=" + praises +
                ", labels=" + labels +
                ", type='" + type + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}

package com.cd.myluntan.entrty;

public class User {
    //id
    String id;
    //账号
     String name;
     //密码
     String pass;
     //性别
     String sex="男";
     //年纪
     String age="18";
     //头像
     String head_url="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1245886665,169279313&fm=26&gp=0.jpg";
     //昵称
     String nick="diandian";
     //签名
    String signaturnre="天天开心";

    public String getSignaturnre() {
        return signaturnre;
    }

    public void setSignaturnre(String signaturnre) {
        this.signaturnre = signaturnre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}

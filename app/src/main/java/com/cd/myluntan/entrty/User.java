package com.cd.myluntan.entrty;

public class User {
    //id
    String id;
    //账号
     String name;
     //密码
     String pass;
     //性别
     String sex;
     //年纪
     String age;
     //头像
     String head_url;
     //昵称
     String nick;
     //签名
    String signaturnre;

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

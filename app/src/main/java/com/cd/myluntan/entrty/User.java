package com.cd.myluntan.entrty;

public class User {
    public static final int TYPE_NOT_IS_FOLLOW=0;
    public static final int TYPE_IS_FOLLOW=1;
    //id
    String id;
    //账号
    String name;
    //密码
    String pass;
    //性别
    String sex = "男";
    //年纪
    String age = "18";
    //头像
    String headurl = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1245886665,169279313&fm=26&gp=0.jpg";
    //昵称
    String nick = "diandian";
    //签名
    String signaturnre = "天天开心";
    //是否关注
    Integer isFollow = TYPE_NOT_IS_FOLLOW;

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

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(Integer isFollow) {
        this.isFollow = isFollow;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", headurl='" + headurl + '\'' +
                ", nick='" + nick + '\'' +
                ", signaturnre='" + signaturnre + '\'' +
                ", isFollow=" + isFollow +
                '}';
    }
}

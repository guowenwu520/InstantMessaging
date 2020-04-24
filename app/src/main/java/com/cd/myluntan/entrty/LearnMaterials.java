package com.cd.myluntan.entrty;

import java.util.Date;

/**
 * 学习资料实体
 */
public class LearnMaterials {
    public static int TYPE_PRICE = -1;

    private String id;
    private User author;//作者
    private String title;//标题
    private String image="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1245886665,169279313&fm=26&gp=0.jpg";//图片
    private int classHour;//课时
    private int userNum;//用户人数
    private int price=TYPE_PRICE;//价格
    private Date createDate;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getClassHour() {
        return classHour;
    }

    public void setClassHour(int classHour) {
        this.classHour = classHour;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "LearnMaterials{" +
                "id='" + id + '\'' +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", classHour=" + classHour +
                ", userNum=" + userNum +
                ", price=" + price +
                ", createDate=" + createDate +
                '}';
    }
}

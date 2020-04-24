package com.cd.myluntan.entrty;

import java.util.ArrayList;
import java.util.Date;

public class Home {
    private String id;
    private User author;//作者
    private String title;//标题
    private String content;//内容
    ArrayList<Comment> comments;//评论
    ArrayList<Praise> praises;//点赞
    ArrayList<Label> labels;//所属标签
    private Date createDate;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<Label> labels) {
        this.labels = labels;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Home{" +
                "id='" + id + '\'' +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", comments=" + comments +
                ", praises=" + praises +
                ", labels=" + labels +
                ", createDate=" + createDate +
                '}';
    }
}

package com.cd.myluntan.entrty;

import java.util.ArrayList;

/**
 * Created by 18179 on 2020/2/4.
 */

public class Comment {
   String id;
   String Dynamic_id;
    String commit_mag;
    String commit_time;
   //评论者
   User user;
   //被评论者
    User usered;
   //回复或者评论
   String type;
   //评论的回复
    ArrayList<Comment> comments;

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDynamic_id() {
        return Dynamic_id;
    }

    public void setDynamic_id(String dynamic_id) {
        Dynamic_id = dynamic_id;
    }

    public String getCommit_mag() {
        return commit_mag;
    }

    public void setCommit_mag(String commit_mag) {
        this.commit_mag = commit_mag;
    }

    public String getCommit_time() {
        return commit_time;
    }

    public void setCommit_time(String commit_time) {
        this.commit_time = commit_time;
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
}

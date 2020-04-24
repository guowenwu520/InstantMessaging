package com.cd.myluntan.entrty;

import java.util.ArrayList;
import java.util.Date;

public class LearnGroup {
    private String id;
    private String title;//标题
    private ArrayList<LearnMaterials> learnMaterials;//学习资料信息
    private Date createDate;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<LearnMaterials> getLearnMaterials() {
        return learnMaterials;
    }

    public void setLearnMaterials(ArrayList<LearnMaterials> learnMaterials) {
        this.learnMaterials = learnMaterials;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

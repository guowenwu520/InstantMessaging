package com.cd.myluntan.entrty;

public class Label {
    String id;
    String dynamicid;
    String labelmag;

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

    public String getLabelmag() {
        return labelmag;
    }

    public void setLabelmag(String labelmag) {
        this.labelmag = labelmag;
    }

    @Override
    public String toString() {
        return "Label{" +
                "id='" + id + '\'' +
                ", dynamicid='" + dynamicid + '\'' +
                ", labelmag='" + labelmag + '\'' +
                '}';
    }
}

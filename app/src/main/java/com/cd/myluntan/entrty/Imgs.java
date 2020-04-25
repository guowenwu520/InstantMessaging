package com.cd.myluntan.entrty;

/**
 * Created by 18179 on 2020/2/2.
 */

public class Imgs {
    public String waiid;
    public String imgurl;

    public Imgs() {
    }

    public String getWaiid() {
        return waiid;
    }

    public void setWaiid(String waiid) {
        this.waiid = waiid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
    public String toString() {
        return "Imgs{" +
                "waiid='" + waiid + '\'' +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }
}

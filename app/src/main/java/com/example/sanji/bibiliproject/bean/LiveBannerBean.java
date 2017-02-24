package com.example.sanji.bibiliproject.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by sanji on 2017/2/15.
 */

public class LiveBannerBean implements Serializable {
    private String title;
    private String img;
    private String remark;
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "LiveBannerBean{" +
                "link='" + link + '\'' +
                ", remark='" + remark + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                '}';
    }


}

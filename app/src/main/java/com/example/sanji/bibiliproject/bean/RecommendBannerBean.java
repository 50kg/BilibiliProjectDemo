package com.example.sanji.bibiliproject.bean;

import java.io.Serializable;

/**
 * Created by sanji on 2017/2/19.
 */

public class RecommendBannerBean implements Serializable {
    private int id;
    private String title;
    private String image;
    private String hash;
    private String uri;
    private boolean is_ad;
    private String request_id;
    private int creative_id;
    private int src_id;
    private boolean is_ad_loc;
    private String ad_cb;
    private String client_ip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isIs_ad() {
        return is_ad;
    }

    public void setIs_ad(boolean is_ad) {
        this.is_ad = is_ad;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getCreative_id() {
        return creative_id;
    }

    public void setCreative_id(int creative_id) {
        this.creative_id = creative_id;
    }

    public int getSrc_id() {
        return src_id;
    }

    public void setSrc_id(int src_id) {
        this.src_id = src_id;
    }

    public boolean isIs_ad_loc() {
        return is_ad_loc;
    }

    public void setIs_ad_loc(boolean is_ad_loc) {
        this.is_ad_loc = is_ad_loc;
    }

    public String getAd_cb() {
        return ad_cb;
    }

    public void setAd_cb(String ad_cb) {
        this.ad_cb = ad_cb;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    @Override
    public String toString() {
        return "RecommendBannerBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", hash='" + hash + '\'' +
                ", uri='" + uri + '\'' +
                ", is_ad=" + is_ad +
                ", request_id='" + request_id + '\'' +
                ", creative_id=" + creative_id +
                ", src_id=" + src_id +
                ", is_ad_loc=" + is_ad_loc +
                ", ad_cb='" + ad_cb + '\'' +
                ", client_ip='" + client_ip + '\'' +
                '}';
    }
}

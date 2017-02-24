package com.example.sanji.bibiliproject.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by sanji on 2017/2/17.
 */

public class LiveContnetBean implements MultiItemEntity, Serializable {

    public static final int TITLE = 1;
    public static final int CONTNET = 2;
    public static final int MORE = 3;

    //title
    private int itemType;
    private int id;
    private String titleName;
    private String titleSrc;
    private int count;


    //content
    private String title;
    private int room_id;
    private int online;
    private String playurl;
    private String face;
    private String contentName;
    private String contentSrc;//详情页里的img


    @Override
    public int getItemType() {
        return itemType;
    }

    public String getContentSrc() {
        return contentSrc;
    }

    public void setContentSrc(String contentSrc) {
        this.contentSrc = contentSrc;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTitleSrc() {
        return titleSrc;
    }

    public void setTitleSrc(String titleSrc) {
        this.titleSrc = titleSrc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getPlayurl() {
        return playurl;
    }

    public void setPlayurl(String playurl) {
        this.playurl = playurl;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    @Override
    public String toString() {
        return "LiveContnetBean{" +
                "itemType=" + itemType +
                ", id=" + id +
                ", titleName='" + titleName + '\'' +
                ", titleSrc='" + titleSrc + '\'' +
                ", count=" + count +
                ", title='" + title + '\'' +
                ", room_id=" + room_id +
                ", online=" + online +
                ", playurl='" + playurl + '\'' +
                ", face='" + face + '\'' +
                ", contentName='" + contentName + '\'' +
                ", contentSrc='" + contentSrc + '\'' +
                '}';
    }
}

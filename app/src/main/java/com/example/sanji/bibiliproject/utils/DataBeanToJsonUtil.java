package com.example.sanji.bibiliproject.utils;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.example.sanji.bibiliproject.bean.LiveBannerBean;
import com.example.sanji.bibiliproject.bean.LiveContnetBean;
import com.example.sanji.bibiliproject.bean.RecommendBannerBean;
import com.youth.banner.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanji on 2017/2/17.
 * 解析类 把json解析bean类
 */

public class DataBeanToJsonUtil {
    @NonNull
    public static List<LiveBannerBean> getLiveBannerBeanData(String jsonString) throws JSONException {
        List<LiveBannerBean> beanList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonString);
        jsonObject = jsonObject.getJSONObject("data");

        List<LiveBannerBean> been = JSON.parseArray(jsonObject.getJSONArray("banner").toString(), LiveBannerBean.class);
        beanList.addAll(been);
        return beanList;
    }


    @NonNull
    public static List<RecommendBannerBean> getRecmmendBannerBeanData(String jsonString) throws JSONException {

        List<RecommendBannerBean> bannerList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        JSONObject hot = jsonArray.getJSONObject(0);
        JSONObject banner = hot.getJSONObject("banner");
        List<RecommendBannerBean> temp = JSON.parseArray(banner.getJSONArray("top").toString(), RecommendBannerBean.class);
        bannerList.addAll(temp);
        return bannerList;
    }


    /**
     * 这个list集合是title+contnet
     * tyep1是title集合
     * type2是contnet集合
     */
    public static List<LiveContnetBean> getLiveTitleAndContentBeanData(String jsonString) throws JSONException {
        List<LiveContnetBean> title_contentList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        jsonObject = jsonObject.getJSONObject("data");
        JSONArray titleContentArray = jsonObject.getJSONArray("partitions");

        //title
        for (int i = 0; i < titleContentArray.length(); i++) {

            LiveContnetBean liveContnetBean = new LiveContnetBean();
            JSONObject title_contnet_obj = titleContentArray.getJSONObject(i);

            //获取title
            JSONObject partition = title_contnet_obj.getJSONObject("partition");
            //又嵌套了一个obj
            JSONObject entrance_icon = partition.getJSONObject("sub_icon");

            liveContnetBean.setId(partition.getInt("id"));
            liveContnetBean.setTitleName(partition.getString("name"));
            liveContnetBean.setCount(partition.getInt("count"));
            //嵌套obj
            liveContnetBean.setTitleSrc((entrance_icon.getString("src")));
            //标题是1
            liveContnetBean.setItemType(1);
            title_contentList.add(liveContnetBean);

            //获取contnet 因为是数组
            JSONArray lives1 = title_contnet_obj.getJSONArray("lives");
            //界面只显示前4个，所以循环4次
            for (int j = 0; j < 4; j++) {
                liveContnetBean = new LiveContnetBean();
                JSONObject lives = lives1.getJSONObject(j);
                //嵌套了一个josn
                JSONObject owner = lives.getJSONObject("owner");
                JSONObject cover = lives.getJSONObject("cover");

                liveContnetBean.setTitle(lives.getString("title"));
                liveContnetBean.setRoom_id(lives.getInt("room_id"));
                liveContnetBean.setOnline(lives.getInt("online"));
                liveContnetBean.setPlayurl(lives.getString("playurl"));

                liveContnetBean.setFace(owner.getString("face"));
                liveContnetBean.setContentName(owner.getString("name"));
                liveContnetBean.setContentSrc(cover.getString("src"));

                //内容的Type是2
                liveContnetBean.setItemType(2);
                title_contentList.add(liveContnetBean);


            }

            //加载更多，空布局 没有数据
            liveContnetBean = new LiveContnetBean();
            liveContnetBean.setItemType(3);
            title_contentList.add(liveContnetBean);
        }
        return title_contentList;
    }
}

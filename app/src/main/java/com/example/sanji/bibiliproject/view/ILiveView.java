package com.example.sanji.bibiliproject.view;

import com.example.sanji.bibiliproject.bean.LiveBannerBean;
import com.example.sanji.bibiliproject.bean.LiveContnetBean;

import java.util.List;

/**
 * 作者：刘超sanji 邮箱：sanji1.dev@gmail.com
 * 创建时间：2017/3/16
 * 描述：
 */

public interface ILiveView {
    void getDataResponse(List<LiveBannerBean> bannerList, List<LiveContnetBean> dataList);

    void getDataFailure(Throwable e);
}

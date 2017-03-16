package com.example.sanji.bibiliproject.view;

import com.example.sanji.bibiliproject.bean.PandaGameBean;
import com.example.sanji.bibiliproject.bean.PandaGameListBean;

import java.util.List;

/**
 * 作者：刘超sanji 邮箱：sanji1.dev@gmail.com
 * 创建时间：2017/3/16
 * 描述：
 */

public interface IPandaGameListView {
    void getDataResponse(List<PandaGameListBean.DataBean.ItemsBean> itemsList, int totalPage, int totalRecord);
    void getDataFailure(Throwable e);
}

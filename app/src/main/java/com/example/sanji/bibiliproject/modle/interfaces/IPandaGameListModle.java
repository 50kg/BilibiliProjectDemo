package com.example.sanji.bibiliproject.modle.interfaces;

import com.example.sanji.bibiliproject.bean.PandaGameBean;
import com.example.sanji.bibiliproject.bean.PandaGameListBean;

import io.reactivex.Observable;

/**
 * 作者：刘超sanji 邮箱：sanji1.dev@gmail.com
 * 创建时间：2017/3/16
 * 描述：
 */

public interface IPandaGameListModle {
    Observable<PandaGameListBean> getPanderGameListData(String name, int pageno, int pagenum);
}

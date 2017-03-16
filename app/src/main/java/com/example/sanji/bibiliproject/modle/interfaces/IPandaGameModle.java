package com.example.sanji.bibiliproject.modle.interfaces;

import com.example.sanji.bibiliproject.bean.PandaGameBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * 作者：刘超sanji 邮箱：sanji1.dev@gmail.com
 * 创建时间：2017/3/16
 * 描述：
 */

public interface IPandaGameModle {
    Observable<PandaGameBean> getPanderGameData();
}

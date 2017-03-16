package com.example.sanji.bibiliproject.modle;

import com.example.sanji.bibiliproject.bean.PandaGameBean;
import com.example.sanji.bibiliproject.modle.interfaces.IPandaGameModle;
import com.example.sanji.bibiliproject.network.RequestManager;

import io.reactivex.Observable;

/**
 * 作者：刘超sanji 邮箱：sanji1.dev@gmail.com
 * 创建时间：2017/3/16
 * 描述：
 */

public class PandaGameModle implements IPandaGameModle {
    @Override
    public Observable<PandaGameBean> getPanderGameData() {
        return RequestManager.getInstance().getPandaClient().getPaderGame("category.list", "game");
    }
}

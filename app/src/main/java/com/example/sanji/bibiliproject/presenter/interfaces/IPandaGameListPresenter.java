package com.example.sanji.bibiliproject.presenter.interfaces;

/**
 * 作者：刘超sanji 邮箱：sanji1.dev@gmail.com
 * 创建时间：2017/3/16
 * 描述：
 */

public interface IPandaGameListPresenter {

    void getPanderGameListData(String name, int pageno, int pagenum);

    void onCreate();

    void onDestroy();
}

package com.example.sanji.bibiliproject.presenter;

import com.example.sanji.bibiliproject.bean.PandaGameBean;
import com.example.sanji.bibiliproject.modle.PandaGameModle;
import com.example.sanji.bibiliproject.modle.interfaces.IPandaGameModle;
import com.example.sanji.bibiliproject.presenter.interfaces.IPandaGamePresenter;
import com.example.sanji.bibiliproject.view.IPandaGameView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：刘超sanji 邮箱：sanji1.dev@gmail.com
 * 创建时间：2017/3/16
 * 描述：
 */

public class PandaGamePresenter implements IPandaGamePresenter {

    private IPandaGameView view;
    private IPandaGameModle modle;
    private Reference<IPandaGameView> reference;

    public PandaGamePresenter(IPandaGameView view) {
        this.view = view;
        modle = new PandaGameModle();
    }

    @Override
    public void getPanderGameData() {
        modle.getPanderGameData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PandaGameBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PandaGameBean pandaGameBean) {
                view.getDataResponse(pandaGameBean.getData());
            }

            @Override
            public void onError(Throwable e) {
                view.getDataFailure(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onCreate() {
        //内存泄露 --->软引用 弱引用
        //添加到软引用中
        reference = new WeakReference<>(view);
    }

    @Override
    public void onDestroy() {
        if (reference != null) {
            //回收
            reference.clear();
            reference = null;
        }
    }
}

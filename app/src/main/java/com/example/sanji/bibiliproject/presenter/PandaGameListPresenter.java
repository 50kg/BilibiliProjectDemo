package com.example.sanji.bibiliproject.presenter;

import com.example.sanji.bibiliproject.bean.PandaGameBean;
import com.example.sanji.bibiliproject.bean.PandaGameListBean;
import com.example.sanji.bibiliproject.modle.PandaGameListModle;
import com.example.sanji.bibiliproject.modle.PandaGameModle;
import com.example.sanji.bibiliproject.modle.interfaces.IPandaGameListModle;
import com.example.sanji.bibiliproject.modle.interfaces.IPandaGameModle;
import com.example.sanji.bibiliproject.presenter.interfaces.IPandaGameListPresenter;
import com.example.sanji.bibiliproject.presenter.interfaces.IPandaGamePresenter;
import com.example.sanji.bibiliproject.utils.Utils;
import com.example.sanji.bibiliproject.view.IPandaGameListView;
import com.example.sanji.bibiliproject.view.IPandaGameView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：刘超sanji 邮箱：sanji1.dev@gmail.com
 * 创建时间：2017/3/16
 * 描述：
 */

public class PandaGameListPresenter implements IPandaGameListPresenter {

    private IPandaGameListView view;
    private IPandaGameListModle modle;
    private Reference<IPandaGameListView> reference;

    public PandaGameListPresenter(IPandaGameListView view) {
        this.view = view;
        modle = new PandaGameListModle();
    }


    @Override
    public void getPanderGameListData(String name, int pageNo, final int pageNum) {
        modle.getPanderGameListData(name, pageNo, pageNum).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PandaGameListBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PandaGameListBean pandaGameListBean) {
                List<PandaGameListBean.DataBean.ItemsBean> itemsList = pandaGameListBean.getData().getItems();

                //总条数
                Integer totalPage = Integer.valueOf(pandaGameListBean.getData().getTotal());
                //总页数
                Integer totalRecord = Utils.getTotalRecord(totalPage, pageNum);
                view.getDataResponse(itemsList, totalPage, totalRecord);
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

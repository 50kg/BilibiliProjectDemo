package com.example.sanji.bibiliproject.presenter;

import com.example.sanji.bibiliproject.modle.interfaces.IRecommendModle;
import com.example.sanji.bibiliproject.modle.RecommendModle;
import com.example.sanji.bibiliproject.presenter.interfaces.IRecommendPresenter;
import com.example.sanji.bibiliproject.utils.DataBeanToJsonUtil;
import com.example.sanji.bibiliproject.view.IRecommendView;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 作者：刘超sanji 邮箱：sanji1.dev@gmail.com
 * 创建时间：2017/3/16
 * 描述：
 */

public class RecommendPresenter implements IRecommendPresenter {

    private IRecommendView view;
    private IRecommendModle modle;
    private Reference<IRecommendView> reference;

    public RecommendPresenter(IRecommendView view) {
        this.view = view;
        modle = new RecommendModle();
    }

    @Override
    public void getRecommendData() {
        modle.getRecommendData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String jsonString = responseBody.string();
                    view.getDataResponse(DataBeanToJsonUtil.getRecmmendBannerBeanData(jsonString));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

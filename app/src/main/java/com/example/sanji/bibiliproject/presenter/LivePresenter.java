package com.example.sanji.bibiliproject.presenter;

import com.example.sanji.bibiliproject.modle.interfaces.ILiveModle;
import com.example.sanji.bibiliproject.modle.LiveModle;
import com.example.sanji.bibiliproject.presenter.interfaces.ILivePresenter;
import com.example.sanji.bibiliproject.utils.DataBeanToJsonUtil;
import com.example.sanji.bibiliproject.view.ILiveView;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.Observable;
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

public class LivePresenter implements ILivePresenter {

    private ILiveModle modle;
    private ILiveView view;
    private Reference<ILiveView> reference;

    public LivePresenter(ILiveView view) {
        this.view = view;
        modle = new LiveModle();
    }

    @Override
    public void getLiveData() {
        Observable<ResponseBody> liveData = modle.getLiveData();
        liveData.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String jsonString = responseBody.string();
                    //解析数据
                    view.getDataResponse(DataBeanToJsonUtil.getLiveBannerBeanData(jsonString), DataBeanToJsonUtil.getLiveTitleAndContentBeanData(jsonString));
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

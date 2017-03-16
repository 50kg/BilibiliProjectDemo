package com.example.sanji.bibiliproject.modle;

import com.example.sanji.bibiliproject.modle.interfaces.ILiveModle;
import com.example.sanji.bibiliproject.network.IRetrofitClient;
import com.example.sanji.bibiliproject.network.RequestManager;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * 作者：刘超sanji 邮箱：sanji1.dev@gmail.com
 * 创建时间：2017/3/16
 * 描述：
 */

public class LiveModle implements ILiveModle {

    @Override
    public Observable<ResponseBody> getLiveData() {
        IRetrofitClient retrofitClient = RequestManager.getInstance().getBilibiliLiveClient();
        return retrofitClient.getLiveInfo("android", "android", "xxhdpi");
    }
}

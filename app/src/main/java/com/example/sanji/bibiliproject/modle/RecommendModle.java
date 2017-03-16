package com.example.sanji.bibiliproject.modle;

import com.example.sanji.bibiliproject.modle.interfaces.IRecommendModle;
import com.example.sanji.bibiliproject.network.IRetrofitClient;
import com.example.sanji.bibiliproject.network.RequestManager;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * 作者：刘超sanji 邮箱：sanji1.dev@gmail.com
 * 创建时间：2017/3/16
 * 描述：
 */

public class RecommendModle implements IRecommendModle {
    @Override
    public Observable<ResponseBody> getRecommendData() {
        IRetrofitClient client = RequestManager.getInstance().getBilibiliAppClient();
        return client.getRecommendInfo("434000", "android");
    }
}

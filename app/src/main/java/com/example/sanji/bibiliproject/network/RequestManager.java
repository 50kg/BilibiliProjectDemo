package com.example.sanji.bibiliproject.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：刘超sanji 邮箱：sanji1.dev@gmail.com
 * 创建时间：2017/3/4
 * 描述：
 */

public class RequestManager {

    private static RequestManager requestManager;


    //先走单例模式
    public static RequestManager getInstance() {
        if (requestManager == null) {
            synchronized (RequestManager.class) {
                if (requestManager == null) {
                    requestManager = new RequestManager();//在调用构造
                }
            }
        }
        return requestManager;
    }

    public IRetrofitClient getPandaClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL_PANDA_GAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IRetrofitClient retrofitClient = retrofit.create(IRetrofitClient.class);
        return retrofitClient;
    }

    public IRetrofitClient getBilibiliAppClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL_RECOMMEND)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IRetrofitClient retrofitClient = retrofit.create(IRetrofitClient.class);
        return retrofitClient;
    }

    public IRetrofitClient getBilibiliLiveClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL_LIVE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IRetrofitClient retrofitClient = retrofit.create(IRetrofitClient.class);
        return retrofitClient;
    }
}

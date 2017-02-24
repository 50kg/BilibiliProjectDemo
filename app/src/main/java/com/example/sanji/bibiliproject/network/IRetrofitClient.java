package com.example.sanji.bibiliproject.network;

import com.example.sanji.bibiliproject.bean.PanderGameBean;
import com.example.sanji.bibiliproject.bean.PanderGameListBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by ShineYang on 16/5/10.
 */
public interface IRetrofitClient {
    /*直播页面*/
    @GET("AppNewIndex/common")
    Call<ResponseBody> getLiveInfo(@Query("_device") String _device,
                                   @Query("platform") String platform,
                                   @Query("scale") String scale);

    /*推荐页面的*/
    @GET("x/v2/show")
    Call<ResponseBody> getRecommendInfo(@Query("build") String build,
                                        @Query("platform") String platform);

    /*熊猫-game*/
    @GET("index.php")
    Call<PanderGameBean> getPaderGame(@Query("method") String method,
                                      @Query("type") String type);

    /*熊猫-gameList*/
//    http://api.m.panda.tv/ajax_get_live_list_by_cate?cate=lol&pageno=1&pagenum=10
    @GET("ajax_get_live_list_by_cate")
    Call<PanderGameListBean> getPaderGameList(@Query("cate") String cate,
                                              @Query("pageno") int pageno,
                                              @Query("pagenum") int pagenum);


}

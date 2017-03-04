package com.example.sanji.bibiliproject.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sanji.bibiliproject.R;
import com.example.sanji.bibiliproject.bean.RecommendBannerBean;
import com.example.sanji.bibiliproject.network.IRetrofitClient;
import com.example.sanji.bibiliproject.network.RequestManager;
import com.example.sanji.bibiliproject.utils.BannerRecommendLoader;
import com.example.sanji.bibiliproject.utils.DataBeanToJsonUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 推荐Fragment
 */
public class RecommendFragment extends Fragment implements OnBannerListener {


    private static final String TAG = "RecommendFragment";
    @BindView(R.id.recommend_banner)
    Banner banner;

    //所有banner的数据
    private List<RecommendBannerBean> bannerData;
    private IRetrofitClient retrofitClient;

    public RecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        ButterKnife.bind(this, view);
        retrofitClient = RequestManager.getInstance().getBilibiliAppClient();

        bannerData = new ArrayList<>();
        addBanner();
        banner.setOnBannerListener(this);
        getData();
        return view;
    }


    private void addBanner() {
        banner.setImages(bannerData)
                .setImageLoader(new BannerRecommendLoader())
                .setIndicatorGravity(BannerConfig.RIGHT)
                .setBannerAnimation(Transformer.DepthPage)
                .start();
    }

    private void getData() {
        //参数
        Call<ResponseBody> call = retrofitClient.getRecommendInfo("434000", "android");
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        String jsonString = response.body().string();
                        bannerData = DataBeanToJsonUtil.getRecmmendBannerBeanData(jsonString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    addBanner();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onError: 网络错误！");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        RecommendBannerBean bannerBean = bannerData.get(position - 1);
        intent.putExtra("title", bannerBean.getTitle());
        intent.putExtra("url", bannerBean.getUri());
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("bannerRecommendData", bannerBean);
//        intent.putExtras(bundle);
        startActivity(intent);
    }

    private static RecommendFragment recommendFragment;

    public static RecommendFragment getInstance() {
        if (recommendFragment == null) {
            recommendFragment = new RecommendFragment();
        }
        return recommendFragment;
    }
}

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
import com.example.sanji.bibiliproject.presenter.RecommendPresenter;
import com.example.sanji.bibiliproject.utils.BannerRecommendLoader;
import com.example.sanji.bibiliproject.utils.DataBeanToJsonUtil;
import com.example.sanji.bibiliproject.view.IRecommendView;
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
public class RecommendFragment extends Fragment implements IRecommendView, OnBannerListener {


    private static final String TAG = "RecommendFragment";
    @BindView(R.id.recommend_banner)
    Banner banner;

    //所有banner的数据
    private List<RecommendBannerBean> bannerList = new ArrayList<>();
    private RecommendPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        ButterKnife.bind(this, view);

        presenter = new RecommendPresenter(this);
        presenter.onCreate();
        addBanner();
        presenter.getRecommendData();
        banner.setOnBannerListener(this);
        return view;
    }


    private void addBanner() {
        banner.setImages(bannerList)
                .setImageLoader(new BannerRecommendLoader())
                .setIndicatorGravity(BannerConfig.RIGHT)
                .setBannerAnimation(Transformer.DepthPage)
                .start();
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
        RecommendBannerBean bannerBean = bannerList.get(position - 1);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void getDataResponse(List<RecommendBannerBean> bannerList) {
        this.bannerList = bannerList;
        addBanner();
    }

    @Override
    public void getDataFailure(Throwable e) {
        Toast.makeText(getContext(), getString(R.string.data_error) + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}

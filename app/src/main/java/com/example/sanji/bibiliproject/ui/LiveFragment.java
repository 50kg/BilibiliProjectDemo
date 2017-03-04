package com.example.sanji.bibiliproject.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.sanji.bibiliproject.MyApp;
import com.example.sanji.bibiliproject.R;
import com.example.sanji.bibiliproject.adapter.LiveMultipleQuickAdapter;
import com.example.sanji.bibiliproject.bean.LiveBannerBean;
import com.example.sanji.bibiliproject.bean.LiveContnetBean;
import com.example.sanji.bibiliproject.network.IRetrofitClient;
import com.example.sanji.bibiliproject.network.RequestManager;
import com.example.sanji.bibiliproject.utils.BannerLiveLoader;
import com.example.sanji.bibiliproject.utils.DataBeanToJsonUtil;
import com.orhanobut.logger.Logger;
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
 * 直播Fragment
 */
public class LiveFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private Banner banner;
    private LiveMultipleQuickAdapter adapter;
    private List<LiveBannerBean> beannerList;//banner所以数据
    private List<LiveContnetBean> title_contentList;//标题+正文所有数据
    private IRetrofitClient retrofitClient;
    private boolean isRefresh = false;   // 判断是否正在下拉刷新


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        ButterKnife.bind(this, view);
        initView();

        //初始化Recycler
        initRecycler();
        //添加头部
        addBanner();

        addListener();
        return view;
    }

    private void initView() {
        //获取retrofitClient
        retrofitClient = RequestManager.getInstance().getBilibiliLiveClient();
        swipe.setColorSchemeResources(R.color.colorAccent);//下拉刷新颜色
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                isRefresh = true;
                getData();//异步获取数据
            }
        });
    }

    private void addListener() {
        //recycleritem的点击事件
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.contnet_item_main:
                        LiveContnetBean item = (LiveContnetBean) adapter.getItem(position);
                        Toast.makeText(getContext(), "你点击了" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), PlayerInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("content", item);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }

            }
        });

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                LiveBannerBean bannerBean = beannerList.get(position);
                intent.putExtra("title", bannerBean.getRemark());
                intent.putExtra("url", bannerBean.getLink());
                startActivity(intent);
            }
        });

        //下拉刷新
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefresh) {
                    swipe.setRefreshing(true);
                    isRefresh = true;
                    getData();
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    private void initRecycler() {
        title_contentList = new ArrayList<>();
        adapter = new LiveMultipleQuickAdapter(title_contentList);
                    /* 关键内容：通过 setSpanSizeLookup 来告诉布局，你的 item 占几个横向单位，
                    如果你横向有 5 个单位，而你返回当前 item 占用 5 个单位，那么它就会看起来单独占用一行 */
        adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                if (title_contentList.get(position).getItemType() == 2) {
                    //是网格
                    return 1;
                }
                return 2;
            }
        });


        //动画
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.isFirstOnly(false);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

    }

    private void getData() {
        //参数
        Call<ResponseBody> call = retrofitClient.getLiveInfo("android", "android", "xxhdpi");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonString = response.body().string();
                        Logger.json(jsonString);
                        //解析数据到
                        beannerList = DataBeanToJsonUtil.getLiveBannerBeanData(jsonString);
                        title_contentList = DataBeanToJsonUtil.getLiveTitleAndContentBeanData(jsonString);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Logger.e("解析错误");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //是否正在刷新
                    if (isRefresh) {
                        //更新正文
                        adapter.setNewData(title_contentList);
                        isRefresh = false;
                        swipe.setRefreshing(false);
                        //更新banner数据
                        banner.update(beannerList);
                    }


                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                Logger.e("网络错误");

                swipe.setRefreshing(false);
            }
        });

    }

    private void addBanner() {
        beannerList = new ArrayList<>();
        View header = LayoutInflater.from(getContext()).inflate(R.layout.banner_recyclerview, null);
        banner = (Banner) header.findViewById(R.id.banner);
        banner.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MyApp.heightPixels / 6));
        banner.setImages(beannerList)
                .setImageLoader(new BannerLiveLoader())
                .setIndicatorGravity(BannerConfig.RIGHT)
                .setBannerAnimation(Transformer.DepthPage)
                .start();
        adapter.addHeaderView(header);

        View LiveHeader = LayoutInflater.from(getContext()).inflate(R.layout.fragment_live_list, null);
        LiveHeader.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        adapter.addHeaderView(LiveHeader);


    }


    private static LiveFragment liveFragment;

    public static LiveFragment getInstance() {
        if (liveFragment == null) {
            liveFragment = new LiveFragment();
        }
        return liveFragment;
    }


}
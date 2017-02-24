package com.example.sanji.bibiliproject.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.sanji.bibiliproject.MyApp;
import com.example.sanji.bibiliproject.R;
import com.example.sanji.bibiliproject.adapter.LiveMultipleQuickAdapter;
import com.example.sanji.bibiliproject.bean.LiveBannerBean;
import com.example.sanji.bibiliproject.bean.LiveContnetBean;
import com.example.sanji.bibiliproject.network.BaseUrl;
import com.example.sanji.bibiliproject.network.IRetrofitClient;
import com.example.sanji.bibiliproject.utils.DataBeanToJsonUtil;
import com.example.sanji.bibiliproject.utils.BannerLiveLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 直播Fragment
 */
public class LiveFragment extends Fragment implements OnBannerClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "LiveFragment";
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    SwipeRefreshLayout swipe;
    private View header;
    private Banner banner;
    private LiveMultipleQuickAdapter adapter;
    //banner所有的数据
    private List<LiveBannerBean> beannerList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);

        initSwipe();
        //填充数据
        initDataAll();

        ButterKnife.inject(this, view);
        return view;
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void initSwipe() {
        swipe.setColorSchemeResources(R.color.colorAccent);//下拉刷新颜色
        swipe.setOnRefreshListener(this);
        //建立子线程开始加载刷新动画
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
            }
        });
    }


    private void initDataAll() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL_LIVE)
                .build();
        IRetrofitClient retrofitClient = retrofit.create(IRetrofitClient.class);

        //参数
        Call<ResponseBody> call = retrofitClient.getLiveInfo("android", "android", "xxhdpi");
        call.enqueue(new Callback<ResponseBody>() {


            private List<LiveContnetBean> title_contentList;

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonString = response.body().string();
                        Log.d(TAG, "onResponse: " + jsonString);
                        //解析数据到
                        beannerList = DataBeanToJsonUtil.getLiveBannerBeanData(jsonString);
                        title_contentList = DataBeanToJsonUtil.getLiveTitleAndContentBeanData(jsonString);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d(TAG, "onResponse: 解析错误");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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
//                    adapter.isFirstOnly(false);

                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    recyclerView.setAdapter(adapter);

                    //添加头部
                    addBanner(beannerList);

                    //recycleritem的点击事件
                    recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
                        @Override
                        public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            Toast.makeText(getContext(), "你点击了" + title_contentList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), PlayerInfoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("content", title_contentList.get(position));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });

                    //全部执行完毕后关闭刷新动画
                    swipe.setRefreshing(false);
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onError: 网络错误！");

                swipe.setRefreshing(false);
            }
        });

    }

    private void addBanner(List<LiveBannerBean> listurl) {
        header = LayoutInflater.from(getContext()).inflate(R.layout.banner_recyclerview, null);
        banner = (Banner) header.findViewById(R.id.banner);
        banner.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MyApp.heightPixels / 6));
        banner.setImages(listurl)
                .setImageLoader(new BannerLiveLoader())
                .setIndicatorGravity(BannerConfig.RIGHT)
                .setBannerAnimation(Transformer.DepthPage)
                .start();
        adapter.addHeaderView(header);

        View LiveHeader = LayoutInflater.from(getContext()).inflate(R.layout.fragment_live_list, null);
        LiveHeader.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        adapter.addHeaderView(LiveHeader);

        banner.setOnBannerClickListener(this);
    }


    public LiveFragment() {
        // Required empty public constructor
    }


    /**
     * Banner点击事件
     *
     * @param position
     */
    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        LiveBannerBean bannerBean = beannerList.get(position - 1);
        intent.putExtra("title", bannerBean.getRemark());
        intent.putExtra("url", bannerBean.getLink());
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("bannerLiveData", bannerBean);
//        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        //重新获取数据
        adapter = null;
        initDataAll();
    }
}
package com.example.sanji.bibiliproject.ui;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.sanji.bibiliproject.R;
import com.example.sanji.bibiliproject.adapter.PanderGameAdapter;
import com.example.sanji.bibiliproject.adapter.PanderGmaeListQuickAdapter;
import com.example.sanji.bibiliproject.bean.PanderGameBean;
import com.example.sanji.bibiliproject.bean.PanderGameListBean;
import com.example.sanji.bibiliproject.network.BaseUrl;
import com.example.sanji.bibiliproject.network.IRetrofitClient;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PanderGameListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.game_list_recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.game_list_swipe)
    SwipeRefreshLayout swipe;
    private PanderGameBean.DataBean data;
    private List<PanderGameListBean.DataBean.ItemsBean> items;
    private boolean loadMoreFalg = true;


    private static final String TAG = "PanderGameListActivity";

    //当前页
    private int page = 1;
    //总页数
    private int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pander_game_list);
        ButterKnife.inject(this);
        data = (PanderGameBean.DataBean) getIntent().getSerializableExtra("data");
        toolbar.setTitle(data.getCname());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用
        initSwipe();
        initData();


    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL_PANDER_GAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IRetrofitClient retrofitClient = retrofit.create(IRetrofitClient.class);

        //参数
        Call<PanderGameListBean> call = retrofitClient.getPaderGameList(data.getEname(), page, 10);
        call.enqueue(new Callback<PanderGameListBean>() {
            @Override
            public void onResponse(Call<PanderGameListBean> call, Response<PanderGameListBean> response) {

                if (response.isSuccessful()) {
                    PanderGameListBean.DataBean data = response.body().getData();
                    items = data.getItems();
                    total = Integer.valueOf(data.getTotal());
                    if (total % 10 != 0) {
                        total = total / 10 + 1;
                    } else {
                        total = total / 10;
                    }
                    final PanderGmaeListQuickAdapter adapter = new PanderGmaeListQuickAdapter(R.layout.item_pander_game_list, items);
                    recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                    recyclerview.setAdapter(adapter);
                    //动画
                    adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);

                    View LiveHeader = LayoutInflater.from(getApplicationContext()).inflate(R.layout.recycler_empty, null);
                    adapter.setEmptyView(LiveHeader);
                    adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            recyclerview.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (page > total) {
                                        adapter.loadMoreEnd();
                                    } else {
                                        //还有数据
                                        if (loadMoreFalg) {
                                            //加载成功
                                            page++;

                                            getMoreData();
                                            adapter.addData(items);
                                            adapter.loadMoreComplete();
                                        } else {
                                            adapter.loadMoreFail();
                                        }

                                    }

                                }
                            }, 1500);
                        }
                    });

                    recyclerview.addOnItemTouchListener(new OnItemChildClickListener() {
                        @Override
                        public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                            Toast.makeText(PanderGameListActivity.this, position+"", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getApplicationContext(), "你点击了" + items.get(position).getName(), Toast.LENGTH_SHORT).show();
                        }
                    });



                    //全部执行完毕后关闭刷新动画
                    swipe.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<PanderGameListBean> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "数据获取失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getMoreData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL_PANDER_GAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IRetrofitClient retrofitClient = retrofit.create(IRetrofitClient.class);

        //参数
        Call<PanderGameListBean> call = retrofitClient.getPaderGameList(data.getEname(), page, 10);
        call.enqueue(new Callback<PanderGameListBean>() {
            @Override
            public void onResponse(Call<PanderGameListBean> call, Response<PanderGameListBean> response) {
                if (response.isSuccessful()) {
                    loadMoreFalg = true;
                    PanderGameListBean.DataBean data = response.body().getData();
                    items = data.getItems();
                }
            }

            @Override
            public void onFailure(Call<PanderGameListBean> call, Throwable t) {
                loadMoreFalg = false;
            }
        });
    }

    private void initSwipe() {
        swipe.setColorSchemeResources(R.color.colorAccent);//下拉刷新颜色
        //下拉刷新页数清0
        page = 1;
        swipe.setOnRefreshListener(this);
        //建立子线程开始加载刷新动画
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
            }
        });
    }

    /**
     * 左上角返回监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        initData();
    }


}

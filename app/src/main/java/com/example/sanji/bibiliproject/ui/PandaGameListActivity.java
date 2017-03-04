package com.example.sanji.bibiliproject.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.sanji.bibiliproject.R;
import com.example.sanji.bibiliproject.adapter.PandaGmaeListQuickAdapter;
import com.example.sanji.bibiliproject.bean.PandaGameBean;
import com.example.sanji.bibiliproject.bean.PandaGameListBean;
import com.example.sanji.bibiliproject.network.IRetrofitClient;
import com.example.sanji.bibiliproject.network.RequestManager;
import com.example.sanji.bibiliproject.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PandaGameListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.include_toolbar)
    Toolbar include_toolbar;
    @BindView(R.id.game_list_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.game_list_swipe)
    SwipeRefreshLayout swipe;
    private PandaGameBean.DataBean data;
    private List<PandaGameListBean.DataBean.ItemsBean> items;

    private boolean isLoadMore = false;  // 判断是否正在加载更多
    private boolean isRefresh = false;   // 判断是否正在下拉刷新

    //每页显示数据
    private int pageNum = 10;
    //当前页
    private int page = 1;
    //总条数
    private int totalPage = 0;
    //总页数
    private int totalRecord = 0;
    private PandaGmaeListQuickAdapter adapter;
    private IRetrofitClient retrofitClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panda_game_list);
        ButterKnife.bind(this);
        data = (PandaGameBean.DataBean) getIntent().getSerializableExtra("data");
        retrofitClient = RequestManager.getInstance().getPandaClient();//获取retrofitClient
        initToolbar();//toolBar
        initSwipe();//初始化下拉刷新操作

        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                isRefresh = true;
                getData();//在这里执行获取数据的方法
            }
        });

        initRecycler();
        initListener();


    }

    private void initListener() {
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Logger.d(isLoadMore + "" + isRefresh);
                if (!isRefresh && !isLoadMore) {
                    isLoadMore = true;
                    page++;
                    getData();
                }

            }
        });

        recyclerview.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PandaGameListBean.DataBean.ItemsBean item = (PandaGameListBean.DataBean.ItemsBean) adapter.getItem(position);
                Toast.makeText(PandaGameListActivity.this, item.getName(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initRecycler() {
        items = new ArrayList<>();
        adapter = new PandaGmaeListQuickAdapter(R.layout.item_pander_game_list, items);//加载空的items
        recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerview.setAdapter(adapter);
        //动画
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);

        View LiveHeader = LayoutInflater.from(getApplicationContext()).inflate(R.layout.recycler_empty, null);
        adapter.setEmptyView(LiveHeader);
    }

    private void initToolbar() {
        setSupportActionBar(include_toolbar);
        getSupportActionBar().setTitle(data.getCname());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用
    }

    private void getData() {
        //参数

        Call<PandaGameListBean> call = retrofitClient.getPadaGameList(data.getEname(), page, pageNum);
        call.enqueue(new Callback<PandaGameListBean>() {
            @Override
            public void onResponse(Call<PandaGameListBean> call, Response<PandaGameListBean> response) {
                if (response.isSuccessful()) {
                    PandaGameListBean.DataBean data = response.body().getData();
                    adapter.addData(data.getItems());


                    if (isRefresh) {
                        //全部执行完毕后关闭刷新动画
                        swipe.setRefreshing(false);
                        adapter.setNewData(data.getItems());
                        isRefresh = false;
                    }

                    //总条数
                    totalPage = Integer.valueOf(data.getTotal());
                    //总页数
                    totalRecord = Utils.getTotalRecord(totalPage, pageNum);
                    //上拉加载
                    if (isLoadMore) {
                        //判断是否是最后一页
                        if (page == totalRecord) {
                            isLoadMore = false;
                            adapter.loadMoreEnd();
                        } else {
                            adapter.addData(data.getItems());
                            adapter.notifyDataSetChanged();
                            isLoadMore = false;
                            adapter.loadMoreComplete();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<PandaGameListBean> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "数据获取失败！", Toast.LENGTH_SHORT).show();
                //加载错误
                adapter.loadMoreFail();
                isLoadMore = false;
                swipe.setRefreshing(false);
                isRefresh = false;

            }
        });
    }


    private void initSwipe() {
        swipe.setColorSchemeResources(R.color.colorAccent);//下拉刷新颜色
        swipe.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        //下拉刷新
        if (!isRefresh && !isLoadMore) {
            page = 1;
            swipe.setRefreshing(true);
            isRefresh = true;
            getData();
        }
    }
}

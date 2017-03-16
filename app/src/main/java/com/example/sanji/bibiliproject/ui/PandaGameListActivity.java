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
import com.example.sanji.bibiliproject.presenter.PandaGameListPresenter;
import com.example.sanji.bibiliproject.presenter.interfaces.IPandaGameListPresenter;
import com.example.sanji.bibiliproject.view.IPandaGameListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PandaGameListActivity extends BaseActivity implements IPandaGameListView {

    @BindView(R.id.include_toolbar)
    Toolbar include_toolbar;
    @BindView(R.id.game_list_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.game_list_swipe)
    SwipeRefreshLayout swipe;
    private PandaGameBean.DataBean data;//上一页传过来的值
    private List<PandaGameListBean.DataBean.ItemsBean> items = new ArrayList<>();

    private boolean isLoadMore = false;  // 判断是否正在加载更多
    private boolean isRefresh = false;   // 判断是否正在下拉刷新

    //每页显示数据
    private int pageNum = 10;
    //当前页
    private int page = 1;

    private PandaGmaeListQuickAdapter adapter;
    private IPandaGameListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panda_game_list);
        ButterKnife.bind(this);
        data = (PandaGameBean.DataBean) getIntent().getSerializableExtra("data");
        presenter = new PandaGameListPresenter(this);
        presenter.onCreate();
        initView();//初始化控件

        //默认加载首页
        presenter.getPanderGameListData(data.getEname(), page, pageNum);
        swipe.setRefreshing(true);
        isRefresh = true;

        initRecycler();
        initListener();


    }

    private void initListener() {
        //下拉刷新
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefresh && !isLoadMore) {
                    page = 1;
                    swipe.setRefreshing(true);
                    isRefresh = true;
                    presenter.getPanderGameListData(data.getEname(), page, pageNum);
                }
            }
        });

        //上拉加载
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (!isRefresh && !isLoadMore) {
                    isLoadMore = true;
                    page++;
                    presenter.getPanderGameListData(data.getEname(), page, pageNum);
                }
            }
        });

        //单击事件
        recyclerview.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PandaGameListBean.DataBean.ItemsBean item = (PandaGameListBean.DataBean.ItemsBean) adapter.getItem(position);
                Toast.makeText(PandaGameListActivity.this, item.getName(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initRecycler() {
        adapter = new PandaGmaeListQuickAdapter(R.layout.item_pander_game_list, items);//加载空的items
        recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerview.setAdapter(adapter);
        //动画
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        View LiveHeader = LayoutInflater.from(getApplicationContext()).inflate(R.layout.recycler_empty, null);
        adapter.setEmptyView(LiveHeader);
    }

    private void initView() {
        setSupportActionBar(include_toolbar);
        getSupportActionBar().setTitle(data.getCname());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用
        swipe.setColorSchemeResources(R.color.colorAccent);//下拉刷新颜色
    }


    /**
     * @param itemsList
     * @param totalPage   总条数
     * @param totalRecord 总页数
     */
    @Override
    public void getDataResponse(List<PandaGameListBean.DataBean.ItemsBean> itemsList, int totalPage, int totalRecord) {
        //加载成功
        if (isRefresh) {
            //全部执行完毕后关闭刷新动画
            swipe.setRefreshing(false);
            adapter.setNewData(itemsList);
            isRefresh = false;
        }

        if (totalRecord != 0) {
            //上拉加载
            if (isLoadMore) {

                isLoadMore = false;
                adapter.addData(itemsList);
                adapter.notifyDataSetChanged();
                //判断是否是最后一页
                if (page >= totalRecord) {
                    adapter.loadMoreEnd();
                } else {
                    adapter.loadMoreComplete();
                }
            }
        } else {
            //总页数是0代表只有一页
            isLoadMore = false;
            adapter.loadMoreEnd();
        }

    }

    @Override
    public void getDataFailure(Throwable e) {
        //加载错误
        Toast.makeText(this, R.string.data_error + e.getMessage(), Toast.LENGTH_SHORT).show();
        //加载错误
        adapter.loadMoreFail();
        isLoadMore = false;
        swipe.setRefreshing(false);
        isRefresh = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}

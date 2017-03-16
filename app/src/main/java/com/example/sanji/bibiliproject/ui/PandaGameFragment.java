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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.sanji.bibiliproject.R;
import com.example.sanji.bibiliproject.adapter.PandaGameQuictAdapter;
import com.example.sanji.bibiliproject.bean.PandaGameBean;
import com.example.sanji.bibiliproject.presenter.PandaGamePresenter;
import com.example.sanji.bibiliproject.presenter.interfaces.IPandaGamePresenter;
import com.example.sanji.bibiliproject.view.IPandaGameView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PandaGameFragment extends Fragment implements IPandaGameView {


    @BindView(R.id.game_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.pander_gmme_swipe)
    SwipeRefreshLayout swipe;
    private List<PandaGameBean.DataBean> data = new ArrayList<>();
    private PandaGameQuictAdapter adapter;
    private boolean isRefresh = false;   // 判断是否正在下拉刷新
    private IPandaGamePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game, container, false);
        ButterKnife.bind(this, view);
        presenter = new PandaGamePresenter(this);
        presenter.onCreate();
        //整体流程，先加载空的数据，初始化其他操作，等待加载
        swipe.setRefreshing(true);
        isRefresh = true;
        presenter.getPanderGameData();
        initRecycler();
        initListener();

        return view;
    }

    private void initListener() {
        recyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), PandaGameListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (PandaGameBean.DataBean) adapter.getData().get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                swipe.setRefreshing(true);
                presenter.getPanderGameData();
            }
        });
    }

    private void initRecycler() {
        swipe.setColorSchemeResources(R.color.colorAccent);//下拉刷新颜色
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new PandaGameQuictAdapter(R.layout.item_pander_game, data);
        recyclerview.setAdapter(adapter);
        View LiveHeader = LayoutInflater.from(getContext()).inflate(R.layout.recycler_empty, null);
        adapter.setEmptyView(LiveHeader);
    }

    private static PandaGameFragment pandaGameFragment;

    public static PandaGameFragment getInstance() {
        if (pandaGameFragment == null) {
            pandaGameFragment = new PandaGameFragment();
        }
        return pandaGameFragment;
    }

    @Override
    public void getDataResponse(List<PandaGameBean.DataBean> data) {
        this.data = data;
        if (isRefresh) {
            adapter.setNewData(data);
            isRefresh = false;
            //全部执行完毕后关闭刷新动画
            swipe.setRefreshing(false);
        }
    }

    @Override
    public void getDataFailure(Throwable e) {
        Toast.makeText(getContext(), getString(R.string.data_error) + e.getMessage(), Toast.LENGTH_SHORT).show();
        isRefresh = false;
        swipe.setRefreshing(false);
        adapter.loadMoreFail();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}

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
import com.example.sanji.bibiliproject.network.IRetrofitClient;
import com.example.sanji.bibiliproject.network.RequestManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PandaGameFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.game_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.pander_gmme_swipe)
    SwipeRefreshLayout swipe;
    private IRetrofitClient retrofitClient;

    private boolean isRefresh = false;   // 判断是否正在下拉刷新
    private List<PandaGameBean.DataBean> data;
    private PandaGameQuictAdapter adapter;

    public PandaGameFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        ButterKnife.bind(this, view);
        retrofitClient = RequestManager.getInstance().getPandaClient();//获取retrofitClient

        //整体流程，先加载空的数据，初始化其他操作，等待加载

        data = new ArrayList<>();
        //建立子线程开始加载刷新动画
        swipe.post(new Runnable() {
            @Override
            public void run() {
                if (!isRefresh) {
                    swipe.setRefreshing(true);
                    isRefresh = true;
                    getData();//在这里获取数据
                }
            }
        });

        initSwipe();
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
                bundle.putSerializable("data", data.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initRecycler() {
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new PandaGameQuictAdapter(R.layout.item_pander_game, data);
        recyclerview.setAdapter(adapter);
        View LiveHeader = LayoutInflater.from(getContext()).inflate(R.layout.recycler_empty, null);
        adapter.setEmptyView(LiveHeader);
    }

    private void initSwipe() {
        swipe.setColorSchemeResources(R.color.colorAccent);//下拉刷新颜色
        swipe.setOnRefreshListener(this);
    }


    private void getData() {

        //参数
        Call<PandaGameBean> call = retrofitClient.getPaderGame("category.list", "game");
        call.enqueue(new Callback<PandaGameBean>() {
            @Override
            public void onResponse(Call<PandaGameBean> call, Response<PandaGameBean> response) {

                data = response.body().getData();

                if (isRefresh) {
                    initRecycler();
                    isRefresh = false;
                    //全部执行完毕后关闭刷新动画
                    swipe.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<PandaGameBean> call, Throwable t) {
                Toast.makeText(getContext(), "数据获取失败！", Toast.LENGTH_SHORT).show();
                isRefresh = false;
                swipe.setRefreshing(false);
            }
        });
    }


    @Override
    public void onRefresh() {
        getData();
    }

    private static PandaGameFragment pandaGameFragment;

    public static PandaGameFragment getInstance() {
        if (pandaGameFragment == null) {
            pandaGameFragment = new PandaGameFragment();
        }
        return pandaGameFragment;
    }
}

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

import com.example.sanji.bibiliproject.R;
import com.example.sanji.bibiliproject.adapter.PanderGameAdapter;
import com.example.sanji.bibiliproject.bean.PanderGameBean;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class PanderGameFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @InjectView(R.id.game_recyclerview)
    RecyclerView recyclerview;
    private SwipeRefreshLayout swipe;

    public PanderGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.pander_gmme_swipe);
        initSwipe();

        initData();


        ButterKnife.inject(this, view);
        return view;
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


    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL_PANDER_GAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IRetrofitClient retrofitClient = retrofit.create(IRetrofitClient.class);

        //参数
        Call<PanderGameBean> call = retrofitClient.getPaderGame("category.list", "game");
        call.enqueue(new Callback<PanderGameBean>() {
            @Override
            public void onResponse(Call<PanderGameBean> call, Response<PanderGameBean> response) {

                final List<PanderGameBean.DataBean> data = response.body().getData();

                recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
                PanderGameAdapter adapter = new PanderGameAdapter(getContext(), data, R.layout.item_pander_game);
                recyclerview.setAdapter(adapter);

                /**
                 * 单击事件接口回调
                 */
                adapter.setOnItemClickListener(new PanderGameAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), PanderGameListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", data.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                //全部执行完毕后关闭刷新动画
                swipe.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<PanderGameBean> call, Throwable t) {
                Toast.makeText(getContext(), "数据获取失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onRefresh() {
        initData();
    }
}

package com.example.sanji.bibiliproject.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sanji.bibiliproject.R;
import com.example.sanji.bibiliproject.bean.CollapsingToolbarLayoutState;
import com.example.sanji.bibiliproject.bean.LiveContnetBean;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PlayerInfoActivity extends BaseActivity {

    @InjectView(R.id.playButton)
    ButtonBarLayout playButton;
    @InjectView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @InjectView(R.id.toolbar_play)
    Toolbar toolbar_play;
    @InjectView(R.id.app_bar)
    AppBarLayout appBar;
    @InjectView(R.id.player_fragment)
    FrameLayout playerFragment;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @InjectView(R.id.imageview)
    ImageView imageview;
    private CollapsingToolbarLayoutState state;
    private LiveContnetBean data;


    private PlayerInfoFragment playerInfoFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);
        ButterKnife.inject(this);

        data = (LiveContnetBean) getIntent().getSerializableExtra("content");

        //设置状态
        initState();
        //设置数据
        setData();
        //视频界面底部的详情和评论
        initInfoAndSay();


    }

    private void initInfoAndSay() {
        playerInfoFragment = new PlayerInfoFragment();
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.player_fragment, playerInfoFragment);
        transaction.commit();
    }


    private void setData() {
        Glide.with(this).load(data.getContentSrc())
                .crossFade()//淡入
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//缓存
                .placeholder(R.drawable.ic_next_video_placeholder)//站位
                .error(R.drawable.img_tips_error_banner_tv)//错误
                .into(imageview);

    }

    private void initState() {
        toolbar_play.setTitle("");
        setSupportActionBar(toolbar_play);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        final AppBarLayout app_bar = (AppBarLayout) findViewById(R.id.app_bar);
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
//                        collapsingToolbarLayout.setTitle("展开");//设置title为EXPANDED

                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        toolbar_play.setTitle("");//设置title不显示
                        playButton.setVisibility(View.VISIBLE);//隐藏播放按钮
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠

                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                            playButton.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                        }
//                        collapsingToolbarLayout.setTitle("中间状态");//设置title为INTERNEDIATE
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });

        //展开
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app_bar.setExpanded(true);

                //延时，等展开动画结束
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(700);//休眠3秒
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        goPlayer();
                    }
                }.start();
                //跳转到播放页面

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到播放页面
                goPlayer();
            }

        });

    }

    private void goPlayer() {
        Intent intent = new Intent(this, IjkPlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("content", data);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}



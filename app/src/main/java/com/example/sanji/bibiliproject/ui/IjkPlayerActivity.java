package com.example.sanji.bibiliproject.ui;

/**
 * Created by sanji on 2017/2/18.
 */

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.bumptech.glide.Glide;
import com.dl7.player.media.IjkPlayerView;
import com.example.sanji.bibiliproject.R;
import com.example.sanji.bibiliproject.bean.LiveContnetBean;

import butterknife.ButterKnife;
import butterknife.InjectView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;

public class IjkPlayerActivity extends AppCompatActivity {

    @InjectView(R.id.player_view)
    IjkPlayerView mPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijk_player);
        ButterKnife.inject(this);

        LiveContnetBean data = (LiveContnetBean) getIntent().getSerializableExtra("content");

        //  以下为配置接口，选择需要的调用
        Glide.with(this).load(data.getContentSrc()).fitCenter().into(mPlayerView.mPlayerThumb);    // 显示界面图

        mPlayerView.init()// 初始化，必须先调用
                .setTitle(data.getContentName() + "的直播间")// 设置标题，全屏时显示
                .alwaysFullScreen()         // 固定全屏
                .enableOrientation()    // 使能重力翻转
                .setVideoPath(data.getPlayurl())    // 设置视频Url，单个视频源可用这个
                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH)  // 指定初始视频源
                .enableDanmaku()        // 使能弹幕功能
                .setDanmakuSource(getResources().openRawResource(R.raw.danmu))   // 添加弹幕资源，必须在enableDanmaku()后调用
                .start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerView.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPlayerView.configurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mPlayerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mPlayerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
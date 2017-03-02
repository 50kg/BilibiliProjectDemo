package com.example.sanji.bibiliproject;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.example.sanji.bibiliproject.ui.MainActivity;
import com.zxy.recovery.core.Recovery;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * Created by sanji on 2017/1/31.
 */

public class MyApp extends Application {

    //屏幕高度
    public static int heightPixels;
    //全局context1
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        heightPixels = dm.heightPixels;

        this.context = getApplicationContext();

        //恢复模式
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)//当应用在后台时发生Crash，是否需要进行恢复
                .recoverStack(true)//是否恢复整个Activity Stack，否则将恢复栈顶Activity
                .mainPage(MainActivity.class)//回退的界面
                .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)//是否静默恢复
                .init(this);

        //滑动返回 必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回
        BGASwipeBackManager.getInstance().init(this);

    }

    //全局Content
    public static Context getContext() {
        return context;
    }


}

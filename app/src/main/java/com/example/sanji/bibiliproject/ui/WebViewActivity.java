package com.example.sanji.bibiliproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sanji.bibiliproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

public class WebViewActivity extends BaseActivity {

    @InjectView(R.id.myWebView)
    WebView mWebView;
    @InjectView(R.id.include_toolbar)
    Toolbar include_toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        setSupportActionBar(include_toolbar);
        getSupportActionBar().setTitle(intent.getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用


        mWebView.setWebViewClient(new WebViewClient()); //在webview里操作二级页面还在webview里显示，不打开自带浏览器
        mWebView.getSettings().setJavaScriptEnabled(true);//加载js可用
        mWebView.loadUrl(intent.getStringExtra("url"));
    }


}

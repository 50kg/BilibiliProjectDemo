package com.example.sanji.bibiliproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sanji.bibiliproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WebViewActivity extends BaseActivity {

    @InjectView(R.id.myWebView)
    WebView mWebView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.inject(this);

//        LiveBannerBean data = (LiveBannerBean) getIntent().getSerializableExtra("bannerLiveData");

        Intent intent = getIntent();

        toolbar.setTitle(intent.getStringExtra("title"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用


        mWebView.setWebViewClient(new WebViewClient()); //在webview里操作二级页面还在webview里显示，不打开自带浏览器
        mWebView.getSettings().setJavaScriptEnabled(true);//加载js可用
        mWebView.loadUrl(intent.getStringExtra("url"));
    }

    //返回键返回上级菜单
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 左上角返回监听
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
}

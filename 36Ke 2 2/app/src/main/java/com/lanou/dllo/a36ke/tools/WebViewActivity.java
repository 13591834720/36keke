package com.lanou.dllo.a36ke.tools;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;

/**
 * Created by dllo on 16/6/25.
 */
public class WebViewActivity extends BaseActivity {
    private WebView webView;
    @Override
    public int initLayout() {
        return R.layout.web_view;
    }
    @Override
    public void initView() {
        webView= (WebView) findViewById(R.id.news_web_view);
    }
    @Override
    public void initData() {
        webView.loadUrl(getIntent().getStringExtra("webUrl"));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());


    }
}

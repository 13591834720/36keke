package com.lanou.dllo.a36ke.news.search;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.tools.SingleRequest;

/**
 * Created by dllo on 16/7/8.
 */
public class SearchMoreActivity extends BaseActivity {
    private  SearchAdapter adapter;
    private ListView listView;
    private  SearchBean bean;


    @Override
    public int initLayout() {
        return R.layout.activity_news_search;
    }
    @Override
    public void initView() {
        listView= (ListView) findViewById(R.id.news_search_list_view);
        findViewById(R.id.rv1).setVisibility(View.GONE);
        findViewById(R.id.search_bg_iv).setVisibility(View.GONE);
        findViewById(R.id.news_search_more).setVisibility(View.GONE);
    }
    @Override
    public void initData() {
        adapter=new SearchAdapter(this);
        listView.setAdapter(adapter);
        getSearchRequest();

    }
    public void getSearchRequest() {
        RequestQueue requestQueue = SingleRequest.getSingleRequest(this).getRequestQueue();
        StringRequest stringRequest = new StringRequest("https://rong.36kr.com/api/mobi/news/search?keyword="+getIntent().getStringExtra("s")+"&page=1&pageSize=20", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, SearchBean.class);
                adapter.setSearchBean(bean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
    }

}
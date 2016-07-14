package com.lanou.dllo.a36ke.discovery.college36ke;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.news.newsdetail.NewsDetailActivity;
import com.lanou.dllo.a36ke.tools.SingleRequest;

/**
 * Created by dllo on 16/6/28.
 */
public class CollegeActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private PullToRefreshListView pullToRefreshListView;
    private CollegeBean bean;
    private CollegeAdapter adapter;
    private ListView listView;
    private int page = 20;
    private TextView mineTitle;
    private ImageView mineBack;


    @Override
    public int initLayout() {
        return R.layout.activity_college_36ke;
    }

    @Override
    public void initView() {
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.dis_college_listiview);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnItemClickListener(this);
        mineTitle= (TextView) findViewById(R.id.mine_title);
        mineTitle.setText("36氪研究院");
        mineBack= (ImageView) findViewById(R.id.mine_back);
        mineBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void initData() {
        adapter = new CollegeAdapter(this);
        listView = pullToRefreshListView.getRefreshableView();
        listView.setAdapter(adapter);
        getRequest();
        initPullToReFresh();

    }

    //解析数据
    public void getRequest() {
        RequestQueue requestQueue = SingleRequest.getSingleRequest(this).getRequestQueue();
        StringRequest stringRequest = new StringRequest("https://rong.36kr.com/api/mobi/news?pageSize=" + page + "&columnId=71&pagingAction=up", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, CollegeBean.class);
                adapter.setBean(bean);
                pullToRefreshListView.onRefreshComplete();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CollegeActivity.this, "没有数据请检查网络 ", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);


    }

    //数据刷新
    public void initPullToReFresh() {
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getRequest();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = page + 20;
                getRequest();
                pullToRefreshListView.setRefreshing(true);
                pullToRefreshListView.onRefreshComplete();

            }
        });

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String feedId = bean.getData().getData().get(position - 1).getFeedId();
        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra("feedId", feedId);
        intent.putExtra("featureImg",bean.getData().getData().get(position).getFeatureImg());
        startActivity(intent);

    }
}

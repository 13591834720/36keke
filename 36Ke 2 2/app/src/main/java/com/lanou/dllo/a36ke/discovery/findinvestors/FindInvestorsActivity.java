package com.lanou.dllo.a36ke.discovery.findinvestors;

import android.content.Intent;
import android.util.Log;
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
import com.lanou.dllo.a36ke.mine.personinfo.PersonalAccountActivity;
import com.lanou.dllo.a36ke.tools.SingleRequest;


/**
 * Created by dllo on 16/6/23.
 */
public class FindInvestorsActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private FindBean bean;
    private FindAdapter adapter;
    private PullToRefreshListView pullToRefreshListView;//刷新
    private int page = 20;
    private ImageView mineBack;
    private TextView mineTitle;
    @Override
    public int initLayout() {
        return R.layout.activity_find_investors;
    }
    @Override
    public void initView() {
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.find_list_view);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mineTitle= (TextView) findViewById(R.id.mine_title);
        mineTitle.setText("寻找投资人");
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
        adapter = new FindAdapter(this);
        getRequest();
        listView = pullToRefreshListView.getRefreshableView();
        listView.setAdapter(adapter);
        initPullToRefresh();
        listView.setOnItemClickListener(this);
    }

    //解析数据
    public void getRequest() {
        RequestQueue requestQueue = SingleRequest.getSingleRequest(this).getRequestQueue();
        StringRequest stringRequest = new StringRequest("https://rong.36kr.com/api/mobi/investor?page=1&pageSize=" + page, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, FindBean.class);
                Log.d("FindInvestorsActivity", "bean.getData().getData().size():" + bean.getData().getData().size());
                adapter.setBean(bean);
                pullToRefreshListView.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FindInvestorsActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    public void initPullToRefresh() {
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
        Intent intent = new Intent(this, PersonalAccountActivity.class);
        intent.putExtra("name", bean.getData().getData().get(position - 1).getUser().getName());
        intent.putExtra("avatarImg", bean.getData().getData().get(position - 1).getUser().getAvatar());
        startActivity(intent);

    }
}

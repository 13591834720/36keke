package com.lanou.dllo.a36ke.equity.all;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lanou.dllo.a36ke.base.BaseFragment;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.equity.equitydetail.EquActivityDetail;
import com.lanou.dllo.a36ke.tools.SingleRequest;

/**
 * Created by dllo on 16/6/17.
 */
public class CompleteFragment extends BaseFragment implements AdapterView.OnItemClickListener, ClickListener {
    private ListView listView;
    private AllBean allBean;
    private AllAdapter adapter;
    private PullToRefreshListView pullToRefreshListView;
    private int page = 20;

    @Override
    public int setLayout() {
        return R.layout.fragment_all_equity;
    }

    @Override
    public void initView(View view) {
       pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.equity_lv);
       pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    @Override
    public void initData() {
        adapter = new AllAdapter(context);
        getAllRequest();
        initPullToReFresh();
        listView = pullToRefreshListView.getRefreshableView();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        adapter.setYouClickListener(this);
    }

    //解析数据
    public void getAllRequest() {
        RequestQueue requestQueue = SingleRequest.getSingleRequest(context).getRequestQueue();
        StringRequest stringRequest = new StringRequest("https://rong.36kr.com/api/mobi/cf/actions/list?page=1&type=raise&pageSize"+page, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                allBean = gson.fromJson(response, AllBean.class);
                adapter.setAllBeen(allBean);
              pullToRefreshListView.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    //数据刷新
    public void initPullToReFresh() {
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getAllRequest();
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page =page+20;
                getAllRequest();
                pullToRefreshListView.setRefreshing(true);
                pullToRefreshListView.onRefreshComplete();
            }
        });
    }
    //item的点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, EquActivityDetail.class);
        startActivity(intent);
    }

    @Override
    public void onIntentClick(int pos) {
        Intent intent = new Intent(context, EquActivityDetail.class);
        intent.putExtra("companyLogo", allBean.getData().getData().get(pos).getCompany_logo());
        intent.putExtra("companyName", allBean.getData().getData().get(pos).getCompany_name());
        intent.putExtra("companyBrief", allBean.getData().getData().get(pos).getCompany_brief());
        intent.putExtra("cf_raising", allBean.getData().getData().get(pos).getCf_raising());
        intent.putExtra("cf_success_raising_offer", allBean.getData().getData().get(pos).getCf_success_raising_offer());
        intent.putExtra("rate", allBean.getData().getData().get(pos).getRate());
        startActivity(intent);
    }
}
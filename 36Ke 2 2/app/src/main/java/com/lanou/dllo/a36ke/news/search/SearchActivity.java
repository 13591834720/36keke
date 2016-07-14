package com.lanou.dllo.a36ke.news.search;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.news.newsdetail.NewsDetailActivity;
import com.lanou.dllo.a36ke.tools.SingleRequest;

/**
 * Created by dllo on 16/6/25.
 * 新闻界面-搜索
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private PullToRefreshListView pullToRefreshListView;//刷新
    private ListView listView;
    private SearchBean bean;//
    private EditText searchEd;
    private ImageView searchImg;//搜索按钮
    private ImageView bgIv;
    private TextView cancel;
    private SearchAdapter adapter;
    private RelativeLayout  searchMore;//搜索更多新闻
    private String s;

    @Override
    public int initLayout() {
        return R.layout.activity_news_search;
    }

    @Override
    public void initView() {
        searchEd = (EditText) findViewById(R.id.news_search_ed);
        searchImg = (ImageView) findViewById(R.id.news_search_img);
        searchImg.setOnClickListener(this);
        cancel = (TextView) findViewById(R.id.news_search_cancel);
        cancel.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.news_search_list_view);
        bgIv = (ImageView) findViewById(R.id.search_bg_iv);

        searchMore = (RelativeLayout) findViewById(R.id.news_search_more);
        searchMore.setOnClickListener(this);
    }

    @Override
    public void initData() {
        adapter = new SearchAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        setSearch();
    }
    //解析数据
    public void getSearchRequest() {
        RequestQueue requestQueue = SingleRequest.getSingleRequest(this).getRequestQueue();
        s = searchEd.getText().toString();
        StringRequest stringRequest = new StringRequest("https://rong.36kr.com/api/mobi/news/search?keyword=" + s + "&page=1&pageSize=3", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, SearchBean.class);
                adapter.setSearchBean(bean);
                if (bean.getData().getData().size() != 0) {
                    listView.setVisibility(View.VISIBLE);
                    bgIv.setVisibility(View.GONE);
                } else {
                    listView.setVisibility(View.GONE);
                    bgIv.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.news_search_cancel:
                finish();
                break;
            case R.id.news_search_more:
                Intent intent=new Intent(this,SearchMoreActivity.class);
                intent.putExtra("s",s);
                startActivity(intent);
                break;
        }
    }

    private void setSearch() {
        searchEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getSearchRequest();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String feedId = bean.getData().getData().get(position).getFeedId();
        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra("feedId", feedId);
        startActivity(intent);


    }
}

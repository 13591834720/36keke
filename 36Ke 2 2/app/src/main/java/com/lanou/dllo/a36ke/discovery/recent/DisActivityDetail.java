package com.lanou.dllo.a36ke.discovery.recent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.lanou.dllo.a36ke.base.MyClickListener;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.tools.SingleRequest;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/23.
 * 发现_近期活动
 */
public class DisActivityDetail extends BaseActivity implements View.OnClickListener, MyClickListener {
    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private DisActBean bean;//
    private DisActAdapter adapter;
    private DisPopupAdapter disPopupAdapter;
    private ListView popupListView;
    private ArrayList<String> datas;
    private LinearLayout type;//活动类型
    private PopupWindow popupWindow;
    private Button typeBtn;
    private String[] ids = {"1", "4", "5", "6", "7"};
    private View view;
    private int page = 1;
    private LinearLayout actTime;//活动时间
    private TextView mineTitle;
    private ImageView mineBack;
    private int position = 0;


    @Override
    public int initLayout() {
        return R.layout.activity_dis_detail;
    }

    @Override
    public void initView() {
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.dis_list_view);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        type = (LinearLayout) findViewById(R.id.dis_act_type);
        type.setOnClickListener(this);
        typeBtn = (Button) findViewById(R.id.dis_act_type_btn);

        actTime = (LinearLayout) findViewById(R.id.dis_act_time);
        actTime.setOnClickListener(this);
        mineTitle= (TextView) findViewById(R.id.mine_title);
        mineTitle.setText("近期活动");
        mineBack= (ImageView) findViewById(R.id.mine_back);
        mineBack.setOnClickListener(this);
    }

    @Override
    public void initData() {
        adapter = new DisActAdapter(this);
        listView = pullToRefreshListView.getRefreshableView();
        listView.setAdapter(adapter);
        getAllRequest();
        initPullToReFresh();
    }

    //数据请求
    public void getAllRequest() {
        RequestQueue requestQueue = SingleRequest.getSingleRequest(this).getRequestQueue();
        StringRequest stringRequest = new StringRequest("https://rong.36kr.com/api/mobi/activity/list?page" + page, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, DisActBean.class);
                adapter.setBean(bean);
                pullToRefreshListView.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DisActivityDetail.this, "数据加载失败,请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private boolean isFirstClick = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dis_act_type:
                if (isFirstClick) {
                    popupWindow.dismiss();
                    isFirstClick = false;
                    break;
                } else {
                    initPopup();
                    popupWindow.showAsDropDown(type, 0, 0);
                    isFirstClick = true;
                    break;
                }

            case  R.id.mine_back:
                finish();
                break;
        }
    }
    private void initPopup() {
        popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view = LayoutInflater.from(this).inflate(R.layout.dis_detail_popup, null);
        popupListView = (ListView) view.findViewById(R.id.popup_list_view);//popup的ListView
        disPopupAdapter = new DisPopupAdapter(this);
        datas = new ArrayList<>();
        datas.add("全部");
        datas.add("Demo ");
        datas.add("氪空间");
        datas.add("股权投资");
        datas.add("企业服务");
        datas.add("急速融资");
        disPopupAdapter.setBean(datas);
        popupListView.setAdapter(disPopupAdapter);
        disPopupAdapter.setClickListener(this);
        popupWindow.setContentView(view);
    }
    @Override
    public void onClick(int pos) {
        position = pos;
        typeBtn.setText(datas.get(pos));
        popupWindow.dismiss();
        if (pos == 0) {
            getAllRequest();
        } else {
            getData(pos);
        }
    }
    public void getData(int pos){
        RequestQueue requestQueue = SingleRequest.getSingleRequest(this).getRequestQueue();
        StringRequest stringRequest = new StringRequest("https://rong.36kr.com/api/mobi/activity/list?page=1&categoryId=" + ids[pos -1] + "&pageSize=" + page , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, DisActBean.class);
                adapter.setBean(bean);
                pullToRefreshListView.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                page++;
                if (position == 0) {
                    getAllRequest();
                } else {
                    getData(position);
                }
                pullToRefreshListView.setRefreshing(true);
                pullToRefreshListView.onRefreshComplete();
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

    }
}

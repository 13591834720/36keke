package com.lanou.dllo.a36ke.news;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

import com.lanou.dllo.a36ke.base.MyClickListener;
import com.lanou.dllo.a36ke.main.Event;
import com.lanou.dllo.a36ke.tools.WebViewActivity;
import com.lanou.dllo.a36ke.news.ketv.TVAdapter;
import com.lanou.dllo.a36ke.news.ketv.TVBean;
import com.lanou.dllo.a36ke.news.newsdetail.NewsDetailActivity;
import com.lanou.dllo.a36ke.tools.SingleRequest;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Created by dllo on 16/6/17.
 */
public class NewsFragment extends BaseFragment implements MyClickListener {
    private PullToRefreshListView pullToRefreshListView;//刷新
    private int page = 20;
    private ListView listView;
    private NewsAdapter adapter;
    private TVAdapter tvAdapter;
    private Bean newsBean;
    private TVBean tvBean;
    private Banner banner;
    private ImageBean imageBean;
    private String id = "all";
    private NewsBroadcastReceiver receiver;
    private int position;

    //绑定布局
    @Override
    public int setLayout() {
        //注册EventBus
        EventBus.getDefault().register(this);
        return R.layout.fragment_news;
    }

    //初始化数据
    @Override
    public void initView(View view) {

        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.news_listView);//刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        adapter = new NewsAdapter(context);
        tvAdapter = new TVAdapter(context);
    }

    //方法调用
    @Override
    public void initData() {
        Log.d("ddd", "position:" + position);
        if (position == 7) {
            getTvResponse();
            listView.setAdapter(tvAdapter);
        } else {
            getNewsRequest();//解析新闻页数据
            getImageRequest();//解析轮播图数据
        }
        initPullToReFresh();//刷新方法
        getReceiver();//注册广播

        if (position == 0) {
            banner.setVisibility(View.VISIBLE);
        } else {
            banner.setVisibility(View.GONE);
        }
    }

    /**
     * 接受MainActivity传过来的position
     *
     * @param event
     */
    @Subscribe
    public void getEvent(Event event) {
        Log.d("NewsFragment", "event.getPosition():" + event.getPosition());
        position = event.getPosition();

    }

    //请求新闻页数据,解析数据
    public void getNewsRequest() {
        RequestQueue requestQueue = SingleRequest.getSingleRequest(context).getRequestQueue();
        StringRequest stringRequest = new StringRequest("https://rong.36kr.com/api/mobi/news?pageSize=" + page + "&columnId=" + id + "&pagingAction=up", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();//解析数据
                newsBean = gson.fromJson(response, Bean.class);
                adapter.setBean(newsBean);
                pullToRefreshListView.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "数据加载失败,请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

    }

    //轮播图解析数据
    public void getImageRequest() {
        RequestQueue requestQueue = SingleRequest.getSingleRequest(context).getRequestQueue();
        StringRequest stringRequest = new StringRequest("https://rong.36kr.com/api/mobi/roundpics/v4", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                imageBean = gson.fromJson(response, ImageBean.class);
                String[] imageUrl = new String[imageBean.getData().getPics().size()];
                for (int i = 0; i < imageBean.getData().getPics().size(); i++) {
                    imageUrl[i] = imageBean.getData().getPics().get(i).getImgUrl();
                }
                banner.setDelayTime(3000);
                banner.setImages(imageUrl);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "数据加载失败,请检查网络", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }
    //氪Tv数据解析
    public void getTvResponse() {
        RequestQueue requestQueue = SingleRequest.getSingleRequest(context).getRequestQueue();
        StringRequest stringRequest = new StringRequest("https://rong.36kr.com/api/mobi/news?pageSize=" + page + "&columnId=" + id + "&pagingAction=up", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                tvBean = gson.fromJson(response, TVBean.class);
                tvAdapter.setTvBean(tvBean);
                pullToRefreshListView.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "数据加载失败,请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    //数据刷新方法
    public void initPullToReFresh() {
        listView = pullToRefreshListView.getRefreshableView();
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getNewsRequest();
                getTvResponse();
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = page + 20;
                getNewsRequest();
                getTvResponse();
                pullToRefreshListView.setRefreshing(true);
                pullToRefreshListView.onRefreshComplete();
            }
        });
        View view = LayoutInflater.from(context).inflate(R.layout.head_news, null);
        banner = (Banner) view.findViewById(R.id._head_news);
        banner.setBannerStyle(Banner.CIRCLE_INDICATOR);//banner加点
        banner.setIndicatorGravity(Banner.CENTER);//点居中
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {//banner的点击事件
            @Override
            public void OnBannerClick(View view, int position) {
                Toast.makeText(context, "点击" + position, Toast.LENGTH_SHORT).show();

            }
        });
        listView.addHeaderView(view);
        listView.setAdapter(adapter);
        adapter.setClickListener(this);
    }

    //注册广播/接受MainActivity传过来的id
    public void getReceiver() {
        receiver = new NewsBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.lanou.dllo.a36ke.main.TITLE");
        context.registerReceiver(receiver, filter);
    }

    //跳转到详情界面
    @Override
    public void onClick(int pos) {
        String feedId = newsBean.getData().getData().get(pos).getFeedId();
        String featureImg = newsBean.getData().getData().get(pos).getFeatureImg();
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("feedId", feedId);
        intent.putExtra("featureImg", featureImg);
        startActivity(intent);
    }

    // 接受MainActivity传过来的id的广播接受者
    class NewsBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            id = intent.getStringExtra("id");
            position = intent.getIntExtra("pos", 0);
            //根据接收到的ID解析数据
            if (intent.getIntExtra("pos", 0) == 0) {
                banner.setVisibility(View.VISIBLE);
                listView.setAdapter(adapter);
                getNewsRequest();
            } else if (intent.getIntExtra("pos", 7) == 7) {
                banner.setVisibility(View.GONE);
                //给listView换一个适配器
                listView.setAdapter(tvAdapter);
                getTvResponse();
            } else {
                banner.setVisibility(View.GONE);
                listView.setAdapter(adapter);
                getNewsRequest();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //取消注册
        EventBus.getDefault().unregister(this);
    }

    //广播取消注册
    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(receiver);
    }


}


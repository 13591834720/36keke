package com.lanou.dllo.a36ke.mine.favorite;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.liteorm.ArticleBean;
import com.lanou.dllo.a36ke.liteorm.LiteOrmSingle;
import com.lanou.dllo.a36ke.liteorm.MyUser;
import com.lanou.dllo.a36ke.news.newsdetail.NewsDetailActivity;
import com.litesuits.orm.LiteOrm;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by dllo on 16/6/25.
 */
public class FavoriteActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mineBack;//返回
    private ListView listView;
    private FavoriteAdapter favoriteAdapter;
    private List<ArticleBean> articleBeens;
    private LiteOrm liteOrm;
    private String featureImg;
    private MyUser user;
    private TextView mineTitle;


    @Override
    public int initLayout() {
        return R.layout.mine_favorite;
    }

    @Override
    public void initView() {
        mineBack = (ImageView) findViewById(R.id.mine_back);
        mineBack.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.favorite_lv);
        mineTitle= (TextView) findViewById(R.id.mine_title);
        mineTitle.setText("我的收藏");

    }

    @Override
    public void initData() {

        getFavorite();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavoriteActivity.this, NewsDetailActivity.class);
                intent.putExtra("feedId", articleBeens.get(position).getFeedId());
                startActivity(intent);
            }
        });

    }

    // 获得收藏文章
    public void getFavorite() {
        articleBeens = new ArrayList<>();
        favoriteAdapter = new FavoriteAdapter(this);
        //查询数据库
        for (ArticleBean bean : LiteOrmSingle.getLiteOrmSingle().getLiteOrm().query(ArticleBean.class)) {
            articleBeens.add(bean);
            favoriteAdapter.setArticleBeans(articleBeens);
            listView.setAdapter(favoriteAdapter);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_back:
                finish();
                break;
        }
    }
}

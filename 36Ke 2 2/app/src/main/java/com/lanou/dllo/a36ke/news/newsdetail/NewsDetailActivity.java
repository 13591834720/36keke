package com.lanou.dllo.a36ke.news.newsdetail;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.text.Html;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.base.MyApplication;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.liteorm.ArticleBean;
import com.lanou.dllo.a36ke.liteorm.LiteOrmSingle;
import com.lanou.dllo.a36ke.liteorm.MyUser;
import com.lanou.dllo.a36ke.mine.login.LoginActivity;
import com.lanou.dllo.a36ke.tools.PicassoCirclTransform;
import com.lanou.dllo.a36ke.tools.SingleRequest;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by dllo on 16/6/17.
 */
public class NewsDetailActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private DetailBean bean;//详情界面数据类
    private TextView title;//标题
    private TextView content;//内容
    private TextView author;//作者
    private TextView summary;//摘要
    private ImageView detailImg;//头像
    private LinearLayout pulldown;//下拉布局
    private PopupWindow popupWindow;//显示下拉的popup
    private ImageView detailMore;//更多
    private LiteOrm liteOrm;//数据库
    private ArticleBean articleBean;
    private MyUser myUser;
    private CheckBox detailFavorite;
    private String feedId;
    private int defalutValue = 0;
    private ImageView popupImg;
    private  TextView popupTitle;

    @Override
    public int initLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initView() {
        title = (TextView) findViewById(R.id.news_detail_titles);
        content = (TextView) findViewById(R.id.news_detail_content);
        author = (TextView) findViewById(R.id.news_detail_author);
        summary = (TextView) findViewById(R.id.news_detail_summary);
        detailImg = (ImageView) findViewById(R.id.news_detail_img);
        pulldown = (LinearLayout) findViewById(R.id.pulldown_layout);
        pulldown.setOnClickListener(this);

        detailMore = (ImageView) findViewById(R.id.news_detail_more);
        detailMore.setOnClickListener(this);

        findViewById(R.id.news_detail_share).setOnClickListener(this);
        findViewById(R.id.news_detail_back).setOnClickListener(this);

        detailFavorite = (CheckBox) findViewById(R.id.news_detail_favorite);
        detailFavorite.setOnCheckedChangeListener(this);
    }

    //解析数据
    @Override
    public void initData() {
        myUser = BmobUser.getCurrentUser(this, MyUser.class);
        Log.d("NewsDetailActivity", "myUser:" + myUser);
        articleBean = new ArticleBean();
        feedId = getIntent().getStringExtra("feedId");
        Log.d("NewsDetailActivity", feedId);
        String url = "https://rong.36kr.com/api/mobi/news/" + feedId;
        RequestQueue requestQueue = SingleRequest.getSingleRequest(this).getRequestQueue();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, DetailBean.class);
                Log.d("NewsDetailActivity", "bean.getData().getColumnId():" + bean.getData().getColumnId());
                title.setText(bean.getData().getTitle());
                if (myUser != null) {
                    articleBean.setColumnName(bean.getData().getColumnName());
                    articleBean.setAvatar(bean.getData().getUser().getAvatar());
                    articleBean.setTitle(bean.getData().getTitle());
                    articleBean.setSummary(bean.getData().getSummary());
                    articleBean.setUserName(myUser.getUsername());
                    articleBean.setFeedId(feedId);
                    articleBean.setFeatureImg(getIntent().getStringExtra("featureImg"));
                    for (ArticleBean articleBean1 : LiteOrmSingle.getLiteOrmSingle().getLiteOrm().query(ArticleBean.class)) {
                        if (feedId.equals(articleBean1.getFeedId())) {
                            detailFavorite.setChecked(true);
                        }
                    }
                }

//                Html.ImageGetter imgGetter = new Html.ImageGetter() {
//                    public Drawable getDrawable(String source) {
//                        Drawable drawable = null;
//                        URL url;
//                        try {
//                            url = new URL(source);
//                            drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            return null;
//                        }
//                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//                                drawable.getIntrinsicHeight());
//                        return drawable;
//                    }
//                };
//
//                struct();
                content.setText(Html.fromHtml(bean.getData().getContent()));
                author.setText(bean.getData().getUser().getName());
                Picasso.with(MyApplication.getContext()).load(bean.getData().getUser().getAvatar()).resize(100, 100).transform(new PicassoCirclTransform()).into(detailImg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewsDetailActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
//    public void struct() {
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads().detectDiskWrites().detectNetwork()
//                .penaltyLog().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
//                .penaltyLog() // 打印logcat
//                .penaltyDeath().build());
//    }

    // 下拉pop
    public void initPopup() {
        popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.item_news_act_detail, null);
        LinearLayout newsLayout = (LinearLayout) view.findViewById(R.id.items_news_popou_layout);
        popupImg= (ImageView) view.findViewById(R.id.news_detail_popup_img);
        popupTitle= (TextView) view.findViewById(R.id.news_detail_titles_popup);

        popupTitle.setText(bean.getData().getTitle());
        newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        popupWindow.setContentView(view);
    }

    //更多按钮的popup
    public void initMorePopup() {
        popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(this).inflate(R.layout.news_detail_more_popup, null);
        SeekBar screenBrightness = (SeekBar) view.findViewById(R.id.news_screen_brightness);
        view.findViewById(R.id.news_font_small).setOnClickListener(this);
        view.findViewById(R.id.news_font_middle).setOnClickListener(this);
        view.findViewById(R.id.news_font_big).setOnClickListener(this);
        view.findViewById(R.id.news_font_large).setOnClickListener(this);
        popupWindow.setContentView(view);
        //设置屏幕亮度
        screenBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setScreenLight(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    // 设置屏幕亮度
    public void setScreenLight(int progress) {
        if (progress < 1) {
            progress = 1;
        } else if (progress > 255) {
            progress = 255;
        }
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.screenBrightness = progress / 255f;
        getWindow().setAttributes(attrs);
        defalutValue = progress;
    }

    private boolean isFirstClick = false;//设置popup初始false

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pulldown_layout:
                if (isFirstClick) {
                    popupWindow.dismiss();
                    isFirstClick = false;
                    break;
                } else {
                    initPopup();
                    popupWindow.showAsDropDown(pulldown, 0, 0);
                    isFirstClick = true;
                    break;
                }
            case R.id.news_detail_back:
                finish();//返回
                break;
            case R.id.news_detail_share:
                showShare();//显示分享
                break;
            case R.id.news_detail_more:
                if (isFirstClick) {
                    popupWindow.dismiss();
                    isFirstClick = false;
                    break;
                } else {
                    initMorePopup();
                    popupWindow.showAtLocation(detailMore, Gravity.BOTTOM, 0, 0);
                    break;
                }
            case R.id.news_font_small:
                content.setTextSize(10);
                break;
            case R.id.news_font_middle:
                content.setTextSize(12);
                break;
            case R.id.news_font_big:
                content.setTextSize(14);
                break;
            case R.id.news_font_large:
                content.setTextSize(24);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    //分享
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        oks.show(this);
    }

    //收藏
    public void setLiteOrm() {
        if (myUser != null) {  //已经登录
            if (LiteOrmSingle.getLiteOrmSingle().getLiteOrm().query(new QueryBuilder<>(ArticleBean.class).where("feedId" + " LIKE ?", new String[]{feedId})).size() == 0) {
                //存到本地数据库
                LiteOrmSingle.getLiteOrmSingle().getLiteOrm().insert(articleBean);
                //存到网络数据库
                articleBean.save(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(NewsDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(NewsDetailActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    //查重
    @Override
    protected void onResume() {
        myUser = BmobUser.getCurrentUser(this,MyUser.class);
        if (myUser != null && bean != null) {
            articleBean.setColumnName(bean.getData().getColumnName());
            articleBean.setAvatar(bean.getData().getUser().getAvatar());
            articleBean.setTitle(bean.getData().getTitle());
            articleBean.setSummary(bean.getData().getSummary());
            articleBean.setUserName(myUser.getUsername());
            articleBean.setFeedId(feedId);
            articleBean.setFeatureImg(getIntent().getStringExtra("featureImg"));
            for (ArticleBean articleBean1 : LiteOrmSingle.getLiteOrmSingle().getLiteOrm().query(ArticleBean.class)) {
                if (feedId.equals(articleBean1.getFeedId())) {
                    detailFavorite.setChecked(true);
                }
            }
        }
        super.onResume();
    }
    //checkbox
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (myUser != null) {
            if (isChecked) {
                //存入数据库中
                setLiteOrm();
            } else {
                // 删除本地数据库数据
                LiteOrmSingle.getLiteOrmSingle().getLiteOrm().delete(new WhereBuilder(ArticleBean.class)
                        .where("feedId" + " LIKE ?", new String[]{feedId}));
                // 删除网络数据
                BmobQuery<ArticleBean> query = new BmobQuery<>();//查询网络数据
                query.addWhereEqualTo("feedId", feedId);
                query.findObjects(this, new FindListener<ArticleBean>() {
                    @Override
                    public void onSuccess(List<ArticleBean> list) {
                        for (ArticleBean newsArticleBean : list) {//遍历表
                            newsArticleBean.delete(NewsDetailActivity.this);
                            Toast.makeText(NewsDetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                    }
                });
            }
        } else {
            detailFavorite.setChecked(false);
            Intent intent=new Intent(NewsDetailActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
}

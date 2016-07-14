package com.lanou.dllo.a36ke.discovery;

import android.content.Intent;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lanou.dllo.a36ke.base.BaseFragment;
import com.lanou.dllo.a36ke.base.MyApplication;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.discovery.recent.DisActivityDetail;
import com.lanou.dllo.a36ke.discovery.business.BusinessActivity;
import com.lanou.dllo.a36ke.discovery.college36ke.CollegeActivity;
import com.lanou.dllo.a36ke.discovery.findinvestors.FindInvestorsActivity;
import com.lanou.dllo.a36ke.discovery.findproject.FindProjectActivity;
import com.lanou.dllo.a36ke.discovery.investor.NewInvestorActivity;
import com.lanou.dllo.a36ke.discovery.startupcompany.StartupCompanyActivity;
import com.lanou.dllo.a36ke.equity.all.AllBean;
import com.lanou.dllo.a36ke.equity.equitydetail.EquActivityDetail;
import com.lanou.dllo.a36ke.equity.ClickToEquity;
import com.lanou.dllo.a36ke.mine.login.LoginActivity;
import com.lanou.dllo.a36ke.liteorm.MyUser;
import com.lanou.dllo.a36ke.news.ImageBean;
import com.lanou.dllo.a36ke.tools.WebViewActivity;
import com.lanou.dllo.a36ke.tools.PicassoCirclTransform;
import com.lanou.dllo.a36ke.tools.SingleRequest;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by dllo on 16/6/17.
 * 发现界面-fragment
 */
public class DiscoveryFragment extends BaseFragment implements View.OnClickListener {
    private ImageBean imageBean;//轮播图数据类
    private Banner banner;//
    private LinearLayout discoveryActivity, findInvestors;
    private TextView seeAll;//查看全部
    private ImageView logo, logoTwo, logoThree;//热门项目头像
    private TextView name, nameTwo, nameThree, brief, briefTwo, briefThree, content, contentTwo, contentThree;//热门项目
    private AllBean bean;//热门项目数据类
    private LinearLayout disBusiness;//我是创业者
    private Button tradeNews;//行业新闻
    private Button projectNews;//项目动态
    private Button investingActivities;//投资活动
    private TextView understandInvest;//快速了解股权投资
    private LinearLayout onLine;//线上路演
    private LinearLayout disInvestor;//发现投资人
    private LinearLayout findProject;//发现好项目
    private LinearLayout collegeLayout;//36氪学院
    private LinearLayout startupCompany;//创业公司

    private LinearLayout disProjectLayout;//热门项目,
    private MyUser user;


    @Override
    public int setLayout() {
        return R.layout.fragment_discovery;
    }

    @Override
    public void initView(View view) {
        //查看全部
        seeAll = (TextView) view.findViewById(R.id.dis_see_all);
        seeAll.setOnClickListener(this);

        banner = (Banner) view.findViewById(R.id.disc_banner);
        banner.setBannerStyle(Banner.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(Banner.CENTER);
        //近期活动
        discoveryActivity = (LinearLayout) view.findViewById(R.id.discovery_activity);
        discoveryActivity.setOnClickListener(this);

        //寻找投资人
        findInvestors = (LinearLayout) view.findViewById(R.id.dis_find_investors);
        findInvestors.setOnClickListener(this);
        //热门项目name
        name = (TextView) view.findViewById(R.id.dis_name_one);
        nameTwo = (TextView) view.findViewById(R.id.dis_name_two);
        nameThree = (TextView) view.findViewById(R.id.dis_name_three);
        //热门项目 头像
        logo = (ImageView) view.findViewById(R.id.dis_logo_one);
        logoTwo = (ImageView) view.findViewById(R.id.dis_logo_two);
        logoThree = (ImageView) view.findViewById(R.id.dis_logo_three);

        brief = (TextView) view.findViewById(R.id.dis_brief_one);
        briefTwo = (TextView) view.findViewById(R.id.dis_brief_two);
        briefThree = (TextView) view.findViewById(R.id.dis_brief_three);

        content = (TextView) view.findViewById(R.id.dis_content_one);
        contentTwo = (TextView) view.findViewById(R.id.dis_content_two);
        contentThree = (TextView) view.findViewById(R.id.dis_brief_three);

        disBusiness = (LinearLayout) view.findViewById(R.id.dis_business_layout);
        disBusiness.setOnClickListener(this);
        //行业新闻
        tradeNews = (Button) view.findViewById(R.id.dis_trade_news);
        tradeNews.setOnClickListener(this);
        //项目动态
        projectNews = (Button) view.findViewById(R.id.dis_project_news);
        projectNews.setOnClickListener(this);
        //投资活动
        investingActivities = (Button) view.findViewById(R.id.dis_investing_activities);
        investingActivities.setOnClickListener(this);
        //快速了解股权投资
        understandInvest = (TextView) view.findViewById(R.id.dis_understand_invest);
        understandInvest.setOnClickListener(this);
        //线上路演
        onLine = (LinearLayout) view.findViewById(R.id.dis_on_line);
        onLine.setOnClickListener(this);
        //寻找投资人
        disInvestor = (LinearLayout) view.findViewById(R.id.dis_new_investor);
        disInvestor.setOnClickListener(this);
        //发现发项目
        findProject = (LinearLayout) view.findViewById(R.id.dis_find_project);
        findProject.setOnClickListener(this);
        //36氪研究院
        collegeLayout = (LinearLayout) view.findViewById(R.id.dis_college_layout);
        collegeLayout.setOnClickListener(this);
        //创业公司
        startupCompany = (LinearLayout) view.findViewById(R.id.dis_startup_company);
        startupCompany.setOnClickListener(this);


        disProjectLayout = (LinearLayout) view.findViewById(R.id.dis_project_layout);
        disProjectLayout.setOnClickListener(this);


    }

    @Override
    public void initData() {
        getImageRequest();//轮播图请求
        getProjectRequest();//热门项目数据请求

    }

    //轮播图数据请求解析
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
                banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
                    @Override
                    public void OnBannerClick(View view, int position) {
                         Toast.makeText(context, "点击了" + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "数据加载失败", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.discovery_activity://近期活动
                Intent intent_activity = new Intent(context, DisActivityDetail.class);
                startActivity(intent_activity);
                break;
            case R.id.dis_find_investors://寻找投资人
                Intent intent_find_investors = new Intent(context, FindInvestorsActivity.class);
                startActivity(intent_find_investors);
                break;
            case R.id.dis_see_all://查看全部
                ((ClickToEquity) getActivity()).toEquity();
                break;

            case R.id.dis_business_layout://我是创业者
                Intent intent_business = new Intent(context, BusinessActivity.class);
                startActivity(intent_business);
                break;
            case R.id.dis_trade_news://行业新闻
                Intent intent_trade_news = new Intent(context, WebViewActivity.class);
                intent_trade_news.putExtra("webUrl", "https://z.36kr.com/m/news?pid=4");
                startActivity(intent_trade_news);

                break;
            case R.id.dis_project_news://项目动态
                Intent intent_project_news = new Intent(context, WebViewActivity.class);
                intent_project_news.putExtra("webUrl", "https://z.36kr.com/m/news?pid=5");
                startActivity(intent_project_news);

                break;
            case R.id.dis_investing_activities://投资活动
                Intent intent_investing_activities = new Intent(context, WebViewActivity.class);
                intent_investing_activities.putExtra("webUrl", "https://z.36kr.com/m/news?pid=6");
                startActivity(intent_investing_activities);
                break;
            case R.id.dis_understand_invest://快速了解股权投资
                Intent intent6 = new Intent(context, WebViewActivity.class);
                intent6.putExtra("webUrl", "https://z.36kr.com/m/class");
                startActivity(intent6);
                break;
            case R.id.dis_on_line://线上路演
                Intent intent_line = new Intent(context, WebViewActivity.class);
                intent_line.putExtra("webUrl", "https://z.36kr.com/m/roadshow/13?ktm_medium=app");
                startActivity(intent_line);
                break;
            case R.id.dis_new_investor://寻找投资人
                Intent intent_new_investor = new Intent(context, NewInvestorActivity.class);
                startActivity(intent_new_investor);
                break;
            case R.id.dis_find_project://发现好项目
                Intent intent_find_project = new Intent(context, FindProjectActivity.class);
                startActivity(intent_find_project);
                break;
            case R.id.dis_college_layout://36氪研究院
                Intent intent_college = new Intent(context, CollegeActivity.class);
                startActivity(intent_college);
                break;
            case R.id.dis_startup_company://创业公司
                user = MyUser.getCurrentUser(context, MyUser.class);
                if (user == null) {
                    Intent intent_startup_company = new Intent(context, LoginActivity.class);
                    startActivity( intent_startup_company);
                } else {
                    Intent  intent_startup_company = new Intent(context, StartupCompanyActivity.class);
                    startActivity( intent_startup_company);
                }
                break;
            case R.id.dis_project_layout://热门项目
                Intent intent_project_layout = new Intent(context, EquActivityDetail.class);
                intent_project_layout.putExtra("companyLogo", bean.getData().getData().get(0).getCompany_logo());
                intent_project_layout.putExtra("companyName", bean.getData().getData().get(0).getCompany_name());
                intent_project_layout.putExtra("companyBrief", bean.getData().getData().get(0).getCompany_brief());
                intent_project_layout.putExtra("cf_raising", bean.getData().getData().get(0).getCf_raising());
                intent_project_layout.putExtra("cf_success_raising_offer", bean.getData().getData().get(0).getCf_success_raising_offer());
                intent_project_layout.putExtra("rate", bean.getData().getData().get(0).getRate());
                startActivity(intent_project_layout);
                break;
        }
    }

    //热门项目 数据请求
    public void getProjectRequest() {
        RequestQueue requestQueue = SingleRequest.getSingleRequest(context).getRequestQueue();
        StringRequest stringRequest = new StringRequest("https://rong.36kr.com/api/mobi/cf/actions/list?page=1&type=all&pageSize=20", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, AllBean.class);
                name.setText(bean.getData().getData().get(0).getCompany_name());
                nameTwo.setText(bean.getData().getData().get(1).getCompany_name());
                nameThree.setText(bean.getData().getData().get(2).getCompany_name());
                Picasso.with(MyApplication.getContext()).load(bean.getData().getData().get(0).getCompany_logo()).
                        resize(100, 100).transform(new PicassoCirclTransform()).into(logo);
                Picasso.with(MyApplication.getContext()).load(bean.getData().getData().get(1).getCompany_logo()).
                        resize(100, 100).transform(new PicassoCirclTransform()).into(logoTwo);
                Picasso.with(MyApplication.getContext()).load(bean.getData().getData().get(2).getCompany_logo()).
                        resize(100, 100).transform(new PicassoCirclTransform()).into(logoThree);
                brief.setText(bean.getData().getData().get(0).getCompany_brief());
                briefTwo.setText(bean.getData().getData().get(1).getCompany_brief());
                briefThree.setText(bean.getData().getData().get(2).getCompany_brief());

                content.setText(bean.getData().getData().get(0).getCf_advantage().get(0).getAdcontent());
                contentTwo.setText(bean.getData().getData().get(1).getCf_advantage().get(0).getAdcontent());
                contentThree.setText(bean.getData().getData().get(1).getCf_advantage().get(0).getAdcontent());
                //纵向viewPager,滚动条

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);

    }


}

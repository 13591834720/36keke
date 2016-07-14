package com.lanou.dllo.a36ke.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.base.MyClickListener;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.discovery.DiscoveryFragment;
import com.lanou.dllo.a36ke.equity.ClickToEquity;
import com.lanou.dllo.a36ke.equity.EquityFragment;
import com.lanou.dllo.a36ke.mine.MineFragment;
import com.lanou.dllo.a36ke.news.NewsFragment;
import com.lanou.dllo.a36ke.news.DrawerAdapter;
import com.lanou.dllo.a36ke.news.search.SearchActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, MyClickListener, View.OnClickListener,
        ClickToEquity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainAdapter adapter;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> bean;
    private DrawerAdapter drawerAdapter;//菜单的Adapter
    private ListView menuListView;
    private DrawerLayout drawerLayout;
    private TextView titles;//标题
    private ImageView menu;//菜单按钮
    private ImageView gift;//礼物
    private ImageView search;//搜索
    private int RESULT_CODE = 120;
    private String title;

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        tabLayout = (TabLayout) findViewById(R.id.main_TabLayout);
        viewPager = (ViewPager) findViewById(R.id.main_ViewPager);
        menuListView = (ListView) findViewById(R.id.news_menu_lv);//抽屉listview
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        titles = (TextView) findViewById(R.id.my_news_title);//标题
        menu = (ImageView) findViewById(R.id.my_news_menu);//菜单按钮
        search = (ImageView) findViewById(R.id.my_news_search);//搜索
        search.setOnClickListener(this);
        gift = (ImageView) findViewById(R.id.my_news_gift);//礼物图标
        gift.setOnClickListener(this);
    }

    @Override
    public void initData() {
        fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(new EquityFragment());
        fragments.add(new DiscoveryFragment());
        fragments.add(new MineFragment());
        adapter = new MainAdapter(getSupportFragmentManager());
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(this);
        menu.setOnClickListener(this);

        initTabIcon();//设置图片
        initBean();//bean加数据
        View view = LayoutInflater.from(this).inflate(R.layout.head_drawer, null);
        view.findViewById(R.id.head_iv).setOnClickListener(this);
        menuListView.addHeaderView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_news_menu:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.head_iv:
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.my_news_search:
                Log.d("MainActivity", "ddd");
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.my_news_gift:
                showCustomAlert();
                Toast.makeText(this, "12", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //设置图片
    private void initTabIcon() {
        int[] selectors = {R.drawable.tab_selector_news, R.drawable.tab_selector_equity, R.drawable.tab_selector_discovery
                , R.drawable.tab_selector_mine};
        for (int i = 0; i < selectors.length; i++) {
            tabLayout.getTabAt(i).setIcon(selectors[i]);
        }
    }

    public void initBean() {
        drawerAdapter = new DrawerAdapter(this);
        bean = new ArrayList<>();
        bean.add("全部");
        bean.add("早期项目");
        bean.add("B轮后");
        bean.add("大公司");
        bean.add("资本");
        bean.add("深度");
        bean.add("研究");
        bean.add("氪TV");
        drawerAdapter.setBean(bean);
        menuListView.setAdapter(drawerAdapter);
        drawerAdapter.setClickListener(this);
    }

    // viewPager滑动监听的方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setTitleBar(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void setTitleBar(int pos) {
        switch (pos) {
            case 0:
                findViewById(R.id.all_titles).setVisibility(View.VISIBLE);
//                title = "新闻";
                titles.setText(title);
                search.setVisibility(View.VISIBLE);
                menu.setVisibility(View.VISIBLE);
                gift.setVisibility(View.GONE);
                break;
            case 1:
                findViewById(R.id.all_titles).setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                titles.setText(adapter.getPageTitle(pos));
                gift.setVisibility(View.VISIBLE);
                menu.setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.all_titles).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.all_titles).setVisibility(View.GONE);
                break;
        }
    }


    // 抽屉点击
    @Override
    public void onClick(int pos) {
        //通过EventBus将position传到NewsFragment
        Event event = new Event(pos);
        EventBus.getDefault().post(event);
        if (pos == 0) {
            title = "新闻";
            titles.setText(title);
        } else {
            title = bean.get(pos);
            titles.setText(bean.get(pos));
        }
        drawerLayout.closeDrawer(Gravity.LEFT);
        String[] ids = {"all", "67", "68", "23", "69", "70", "71", "tv"};
        Intent sendIntent = new Intent("com.lanou.dllo.a36ke.main.TITLE");
        //根据pos 将响应的ID串给NewsFragment
        sendIntent.putExtra("id", ids[pos]);
        sendIntent.putExtra("pos", pos);
        sendBroadcast(sendIntent);
    }


    //股权投资 礼物图标的Dialog
    private void showCustomAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        //布局加载器 拿到View
        View view = LayoutInflater.from(this).inflate(R.layout.gift_dialog, null);
        //通过id 找到控件
        final Button giftBtn = (Button) view.findViewById(R.id.news_gift_butn);
        //加载自定义View
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String gift_btn = giftBtn.getText().toString();
                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();//每次都放在最后写
    }
    //查看全部
    @Override
    public void toEquity() {
        viewPager.setCurrentItem(1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE) {
            viewPager.setCurrentItem(1);

        }
    }
}

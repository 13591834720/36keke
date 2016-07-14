package com.lanou.dllo.a36ke.mine.order;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/25.
 * 我的-我的订单
 */
public class OrderActivity extends BaseActivity {
    private TabLayout orderTabLayout;
    private ViewPager orderViewPager;
    private OrderAdapter adapter;
    private ArrayList<Fragment>fragments;

    @Override
    public int initLayout() {
        return R.layout.mine_order;
    }

    @Override
    public void initView() {
        orderTabLayout= (TabLayout) findViewById(R.id.mine_order_Tablayout);
        orderViewPager= (ViewPager) findViewById(R.id.mine_order_viewpager);

    }

    @Override
    public void initData() {
        adapter=new OrderAdapter(getSupportFragmentManager());
        fragments=new ArrayList<>();
        fragments.add(new OrderAllFragment());
        fragments.add(new OrderPaidFragment());
        fragments.add(new OrderUnpaidFragment());
        adapter.setFragments(fragments);
        orderViewPager.setAdapter(adapter);
        orderTabLayout.setupWithViewPager(orderViewPager);

    }
}

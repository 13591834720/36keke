package com.lanou.dllo.a36ke.discovery.findproject;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/28.
 * 发现-发现好项目
 */
public class FindProjectActivity  extends BaseActivity{
    private FindProjectAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment>fragments;
    @Override
    public int initLayout() {
        return R.layout.activity_dis_find_project;
    }
    @Override
    public void initView() {
        tabLayout= (TabLayout) findViewById(R.id.dis_find_project_tablayout);
        viewPager= (ViewPager) findViewById(R.id.dis_find_project_viewpager);
    }
    @Override
    public void initData() {
        adapter=new FindProjectAdapter(getSupportFragmentManager());
        fragments=new ArrayList<>();
        fragments.add(new DayFragment());
        fragments.add(new WeekFragment());
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}

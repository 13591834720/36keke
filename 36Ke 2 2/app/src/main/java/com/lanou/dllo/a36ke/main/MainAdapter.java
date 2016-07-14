package com.lanou.dllo.a36ke.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/17.
 */
public class MainAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment>fragments;

    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    private String []titles={"新闻","股权投资","发现","我的"};

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments!=null&&fragments.size()>0?fragments.get(position):null;
    }

    @Override
    public int getCount() {
        return fragments!=null&&fragments.size()>0?fragments.size():0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}

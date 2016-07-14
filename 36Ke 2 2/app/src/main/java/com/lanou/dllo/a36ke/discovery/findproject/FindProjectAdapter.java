package com.lanou.dllo.a36ke.discovery.findproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/28.
 */
public class FindProjectAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment>fragments;

    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }
   private String[]titles={"日榜","周榜"};


    public FindProjectAdapter(FragmentManager fm) {
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

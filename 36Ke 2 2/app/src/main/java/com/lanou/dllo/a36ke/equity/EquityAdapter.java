package com.lanou.dllo.a36ke.equity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/17.
 */

public class EquityAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment>fragments;
    private String[]titles1={"全部","募资中","募资完成","融资成功"};

    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public EquityAdapter(FragmentManager fm) {
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
        return titles1[position];
    }
}

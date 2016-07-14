package com.lanou.dllo.a36ke.equity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lanou.dllo.a36ke.base.BaseFragment;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.equity.EquityAdapter;
import com.lanou.dllo.a36ke.equity.all.AllFragment;
import com.lanou.dllo.a36ke.equity.all.CompleteFragment;
import com.lanou.dllo.a36ke.equity.all.RaisingFragment;
import com.lanou.dllo.a36ke.equity.all.SuccessFragment;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/17.
 */
public class EquityFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EquityAdapter adapter;
    private ArrayList<Fragment>fragments;

    @Override
    public int setLayout() {
        return R.layout.fragment_equity_highlight;
    }

    @Override
    public void initView(View view) {
        tabLayout= (TabLayout) view.findViewById(R.id.equity_tablayout);
        viewPager= (ViewPager) view.findViewById(R.id.equity_viewPager);
        fragments=new ArrayList<>();
        fragments.add(new AllFragment());
        fragments.add(new RaisingFragment());
        fragments.add(new CompleteFragment());
        fragments.add(new SuccessFragment());
        adapter=new EquityAdapter(getFragmentManager());
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public void initData() {
    }
}

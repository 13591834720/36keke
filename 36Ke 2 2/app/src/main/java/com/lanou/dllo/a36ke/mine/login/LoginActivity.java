package com.lanou.dllo.a36ke.mine.login;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/27.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment>fragments;
    private  LoginAdapter adapter;
    private ImageView mineClose;
    @Override
    public int initLayout() {
        return R.layout.mine_login;
    }

    @Override
    public void initView() {
        tabLayout= (TabLayout) findViewById(R.id.mine_tablayout);
        viewPager= (ViewPager) findViewById(R.id.mine_view_pager);
        fragments=new ArrayList<>();
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
        adapter=new LoginAdapter(getSupportFragmentManager());
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        mineClose= (ImageView) findViewById(R.id.mine_icon_close);
        mineClose.setOnClickListener(this);

    }
    @Override
    public void initData() {
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_icon_close:
                finish();
                break;
        }
    }
}

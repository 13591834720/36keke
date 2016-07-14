package com.lanou.dllo.a36ke.mine;

import android.view.View;
import android.widget.ImageView;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;

/**
 * Created by dllo on 16/6/25.
 * 我的-跟投人认证
 */
public class AuthenticationActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mineBack;
    @Override
    public int initLayout() {
        return R.layout.mine_authentication;
    }
    @Override
    public void initView() {
        mineBack = (ImageView) findViewById(R.id.mine_back);
        mineBack.setOnClickListener(this);
    }
    @Override
    public void initData() {
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_back:
                finish();
                break;
        }
    }
}

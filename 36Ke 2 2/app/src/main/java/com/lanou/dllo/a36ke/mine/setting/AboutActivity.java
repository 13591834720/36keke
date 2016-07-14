package com.lanou.dllo.a36ke.mine.setting;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;

/**
 * Created by dllo on 16/6/29.
 * 我的 -设置-关于36氪
 */
public class AboutActivity extends BaseActivity {
    private TextView mineTitle;
    private ImageView mineBack;

    @Override
    public int initLayout() {
        return R.layout.mine_setting_about;
    }

    @Override
    public void initView() {
        mineTitle= (TextView) findViewById(R.id.mine_title);
        mineTitle.setText("关于");
        mineBack= (ImageView) findViewById(R.id.mine_back);
        mineBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
    }


}

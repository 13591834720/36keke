package com.lanou.dllo.a36ke.mine.setting;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;

/**
 * Created by dllo on 16/6/29.
 * 我的-设置-消息设置
 */
public class InfoSettingsActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ImageView mineBack;//返回
    private Switch infoSettingSwitch;//消息推送
    private  CheckBox allDay;//全天开启
    private CheckBox duringTheDay;//只白天开启
    @Override
    public int initLayout() {
        return R.layout.mine_info_settings;
    }

    @Override
    public void initView() {
        mineBack= (ImageView) findViewById(R.id.mine_back);//返回
        mineBack.setOnClickListener(this);
        infoSettingSwitch= (Switch) findViewById(R.id.info_setting_switch);//消息推送
        infoSettingSwitch.setOnCheckedChangeListener(this);
        allDay= (CheckBox) findViewById(R.id.info_setting_all_day);//全天开启
        allDay.setOnCheckedChangeListener(this);
        duringTheDay= (CheckBox) findViewById(R.id.info_setting_during_the_day);//只在白天开启
        duringTheDay.setOnCheckedChangeListener(this);
    }
    @Override
    public void initData() {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_back://返回
                finish();
                break;
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}

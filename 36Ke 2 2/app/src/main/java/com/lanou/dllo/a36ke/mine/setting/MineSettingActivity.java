package com.lanou.dllo.a36ke.mine.setting;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.liteorm.MyUser;

import cn.bmob.v3.BmobUser;

/**
 * Created by dllo on 16/6/27.
 * 我的-设置界面
 */
public class MineSettingActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout aboutLayout;//关于36氪
    private TextView mineSetting;//消息设置
    private Switch switchWifi;//设置wifi
    private Button outBtn;//退出登录
    private ImageView mineBack;
    private TextView mineTitle;


    @Override
    public int initLayout() {
        return R.layout.mine_setting;
    }

    @Override
    public void initView() {
        aboutLayout = (RelativeLayout) findViewById(R.id.mine_about_layout);//关于36氪
        aboutLayout.setOnClickListener(this);
        mineSetting = (TextView) findViewById(R.id.mine_info_setting);//消息设置
        mineSetting.setOnClickListener(this);
        switchWifi = (Switch) findViewById(R.id.mine_about_switch);//设置wifi
        outBtn = (Button) findViewById(R.id.mine_out_btn);//退出登录
        outBtn.setOnClickListener(this);
        mineBack= (ImageView) findViewById(R.id.mine_back);//返回
        mineBack.setOnClickListener(this);
//        mineTitle.setText("设置");

    }

    @Override
    public void initData() {
        switchWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ToggleWiFi(true);
                    Toast.makeText(getApplicationContext(), "WiFi已开启～", Toast.LENGTH_LONG).show();
                } else {
                    ToggleWiFi(false);
                    Toast.makeText(getApplicationContext(), "WiFi已关闭!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //设置wifi
    private void ToggleWiFi(boolean status) {
        WifiManager wifiManager = (WifiManager) this.getSystemService(this.WIFI_SERVICE);
        if (status == true && !wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        } else if (status == false && wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_about_layout://关于36氪
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_info_setting://消息设置
                Intent intent1 = new Intent(this, InfoSettingsActivity.class);
                startActivity(intent1);
                break;
            case R.id.mine_out_btn://退出登录
                initOut();
                finish();
                break;
            case R.id.mine_back://返回
                finish();
                break;
        }
    }
    //退出登录
   public void initOut(){
        MyUser.logOut(this);
        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        Intent outIntent = new Intent("com.lanou.dllo.a36ke.SETHEAD");
        sendBroadcast(outIntent);
    }
}

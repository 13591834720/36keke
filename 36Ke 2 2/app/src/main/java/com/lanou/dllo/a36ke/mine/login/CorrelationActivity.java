package com.lanou.dllo.a36ke.mine.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.liteorm.MyUser;

import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by dllo on 16/7/8.
 */
public class CorrelationActivity extends BaseActivity implements View.OnClickListener{
    private MyUser user;
    private Platform qq;
    private EditText nameEt;
    private EditText passwordEt;
    @Override
    public int initLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {
        findViewById(R.id.share_qq).setVisibility(View.GONE);
        findViewById(R.id.share_weibo).setVisibility(View.GONE);
        findViewById(R.id.mine_login).setOnClickListener(this);
        nameEt = (EditText) findViewById(R.id.mine_name);
        passwordEt = (EditText) findViewById(R.id.mine_psw);
    }

    @Override
    public void initData() {
        user = new MyUser();
    }
    void initUser(){
        qq = ShareSDK.getPlatform(QQ.NAME);
        user.setUsername(nameEt.getText().toString());
        user.setPassword(passwordEt.getText().toString());
        user.setThirdName(qq.getDb().getUserName());
        user.setUserPassword(passwordEt.getText().toString());
        user.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                //通知我的页面登录
                user.update(CorrelationActivity.this);
                Intent intent = new Intent("com.lanou.dllo.a36ke.SETHEAD");
                sendBroadcast(intent);
                Toast.makeText(CorrelationActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(CorrelationActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_login:
                initUser();
                break;
        }
    }
}
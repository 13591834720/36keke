package com.lanou.dllo.a36ke.mine.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.liteorm.MyUser;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by dllo on 16/6/30.
 */
public class SettingPswActivity extends BaseActivity implements View.OnClickListener {
    private EditText phoneNumberEt;
    private EditText detailNameEt;
    private EditText detailSurePasswordEt;
    private EditText detailPasswordEt;
    private Button resignBtn;

    @Override
    public int initLayout() {
        return R.layout.activity_mine_register_detail;
    }

    @Override
    public void initView() {
        phoneNumberEt = (EditText) findViewById(R.id.resign_detail_phone_number_et);
        phoneNumberEt.setText(getIntent().getStringExtra("phoneNumber"));

        detailNameEt = (EditText) findViewById(R.id.resign_detail_name_et);
        detailPasswordEt = (EditText) findViewById(R.id.resign_detail_password_et);
        detailSurePasswordEt = (EditText) findViewById(R.id.resign_detail_sure_password_et);
        resignBtn = (Button) findViewById(R.id.resign_detail_sure_btn);

        resignBtn.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.resign_detail_sure_btn:
                initBmob();
                break;
        }
    }

    //注册
    public void initBmob() {
        MyUser user = new MyUser();
        user.setUsername(detailNameEt.getText().toString());
        user.setMobilePhoneNumber(phoneNumberEt.getText().toString());
        if (detailPasswordEt.getText().toString().equals(detailSurePasswordEt.getText().toString())) {
            user.setPassword(detailPasswordEt.getText().toString());
        } else {
            Toast.makeText(this, "请确认密码", Toast.LENGTH_SHORT).show();
        }
        //注意：不能用save方法进行注册
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                //发送广播通知登录界面的name和password改变
                Intent intent = new Intent("com.lanou.dllo.a36ke.mine.login.USER");
                intent.putExtra("name", detailNameEt.getText().toString());
                intent.putExtra("password", detailPasswordEt.getText().toString());
                sendBroadcast(intent);
                Toast.makeText(SettingPswActivity.this, "注册成功:", Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(SettingPswActivity.this, ("注册失败:" + msg), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

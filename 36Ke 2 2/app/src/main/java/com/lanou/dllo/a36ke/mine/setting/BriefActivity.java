package com.lanou.dllo.a36ke.mine.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.mine.AccountActivity;

/**
 * Created by dllo on 16/7/4.
 * 账号信息-简介页面
 */
public class BriefActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mineBack;//返回
    private TextView save;//保存
    private EditText mineBriefEd;//简介内容
    private final int RESULT_CODE = 100;
    private TextView mineTitle;


    @Override
    public int initLayout() {
        return R.layout.activity_mine_brief;
    }
    @Override
    public void initView() {
        mineBack = (ImageView) findViewById(R.id.mine_back);
        mineBack.setOnClickListener(this);
        mineBriefEd = (EditText) findViewById(R.id.mine_brief_ed);
        mineBriefEd.setOnClickListener(this);
        save = (TextView) findViewById(R.id.mine_save);
        save.setOnClickListener(this);
        mineTitle= (TextView) findViewById(R.id.mine_title);
        mineTitle.setText("简介");

    }
    @Override
    public void initData() {
        mineBriefEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (mineBriefEd.getText().length() == 0) {
                    save.setVisibility(View.GONE);
                } else {
                    save.setVisibility(View.VISIBLE);
                }
            }
        });

        getBrief();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_back://返回
                finish();
                break;
            case R.id.mine_save://保存
                String brief = mineBriefEd.getText().toString();
                Intent intent = new Intent(BriefActivity.this, AccountActivity.class);
                intent.putExtra("brief", brief);
                setResult(100,intent);
                finish();
                Toast.makeText(this, "保存", Toast.LENGTH_SHORT).show();
                saveBrief(brief);
                break;
        }

    }

    // 保存简介
    public void saveBrief(String brief){
        SharedPreferences sharedPreferences = getSharedPreferences("brief",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("brief", brief);
        editor.commit();
    }

    // 读取简介
    public void getBrief(){
        SharedPreferences sharedPreferences = getSharedPreferences("brief", Context.MODE_PRIVATE);
        String mBrief = sharedPreferences.getString("brief", "空空如也~~~");
        mineBriefEd.setText(mBrief);
    }
}

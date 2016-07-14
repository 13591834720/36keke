package com.lanou.dllo.a36ke.mine;

import android.view.View;
import android.widget.Button;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;

/**
 * Created by dllo on 16/6/25.
 * 我的-我的投资公司
 */
public class CompanyActivity extends BaseActivity implements View.OnClickListener {
    private Button seeAllBtn;
    private int RESULT_CODE = 120;

    @Override
    public int initLayout() {
        return R.layout.mine_company;
    }

    @Override
    public void initView() {
        seeAllBtn = (Button) findViewById(R.id.mine_see_all_btn);
        seeAllBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_see_all_btn:
                setResult(RESULT_CODE, null);
                finish();
                break;
        }
    }
}

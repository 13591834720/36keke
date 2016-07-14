package com.lanou.dllo.a36ke.equity.equitydetail;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;
import com.squareup.picasso.Picasso;

/**
 * Created by dllo on 16/6/23.
 */
public class EquActivityDetail extends BaseActivity{
    private ImageView headIv;//头像
    private TextView companyName;//公司名称
    private  TextView companyBrief;//公司简介
    private  TextView equityRaising;
    private TextView raisingOffer;
    private ProgressBar progressBar;

    @Override
    public int initLayout() {
        return R.layout.activity_equity_detail;
    }

    @Override
    public void initView() {

        headIv = (ImageView) findViewById(R.id.equity_detail_head_iv);
        companyName= (TextView) findViewById(R.id.equity_company_name);
        companyBrief= (TextView) findViewById(R.id.equity_company_brief);
        equityRaising= (TextView) findViewById(R.id.equity_raising);
        raisingOffer= (TextView) findViewById(R.id.cf_success_raising_offer);
        progressBar= (ProgressBar) findViewById(R.id.all_raising_progress_bar);


    }

    @Override
    public void initData() {
        Picasso.with(this).load(getIntent().getStringExtra("companyLogo")).into(headIv);
        companyName.setText(getIntent().getStringExtra("companyName"));
        companyBrief.setText(getIntent().getStringExtra("companyBrief"));
        equityRaising.setText(getIntent().getStringExtra("cf_raising"));
        raisingOffer.setText(getIntent().getStringExtra("cf_success_raising_offer"));
        progressBar.setProgress((int) (getIntent().getDoubleExtra("rate",0) * 100));
        findViewById(R.id.mine_title).setVisibility(View.GONE);
        findViewById(R.id.mine_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

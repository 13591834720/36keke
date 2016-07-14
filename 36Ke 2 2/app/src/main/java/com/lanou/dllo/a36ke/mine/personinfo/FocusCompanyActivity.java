package com.lanou.dllo.a36ke.mine.personinfo;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.liteorm.MyUser;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by dllo on 16/7/5.
 */
public class FocusCompanyActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mineBack;
    private FocusCompanyAdapter adapter;
    private ListView listView;
    private List<InfoBean> infoBeen;
    private MyUser myUser;
    private TextView mineTitle;

    @Override
    public int initLayout() {
        return R.layout.mine_focus_company_activity;
    }

    @Override
    public void initView() {
        mineBack = (ImageView) findViewById(R.id.mine_back);
        mineBack.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.mine_focus_company);
        mineTitle = (TextView) findViewById(R.id.mine_title);
        mineTitle.setText("关注的公司");

    }

    @Override
    public void initData() {
        initQueryData();

    }

    //查数据
    void initQueryData() {
        myUser = BmobUser.getCurrentUser(this, MyUser.class);
        adapter = new FocusCompanyAdapter(this);
        listView.setAdapter(adapter);
        if (myUser != null) {
            BmobQuery<InfoBean> query = new BmobQuery<>();
            query.findObjects(this, new FindListener<InfoBean>() {
                @Override
                public void onSuccess(List<InfoBean> list) {
                    adapter.setInfoBean(list);
                }

                @Override
                public void onError(int i, String s) {

                }
            });

        }


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

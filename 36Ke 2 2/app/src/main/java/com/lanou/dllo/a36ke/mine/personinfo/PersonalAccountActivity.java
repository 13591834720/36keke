package com.lanou.dllo.a36ke.mine.personinfo;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.liteorm.MyUser;
import com.lanou.dllo.a36ke.mine.AccountActivity;
import com.lanou.dllo.a36ke.tools.PicassoCirclTransform;
import com.lanou.dllo.a36ke.tools.RoundDrawable;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by dllo on 16/6/29.
 */
public class PersonalAccountActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mineIconBack;//返回
    private TextView mineEdit;//编辑
    private  ImageView mineShare;//分享
    private  TextView personalName;//用户昵称
    private LinearLayout focusCompany;//关注的公司
    private  ImageView personalImg;//头像

    @Override
    public int initLayout() {
        return R.layout.activity_mine_personal_account;
    }

    @Override
    public void initView() {
        mineEdit= (TextView) findViewById(R.id.mine_edit);//编辑
        mineEdit.setOnClickListener(this);

        mineIconBack= (ImageView) findViewById(R.id.mine_icon_back);//返回
        mineIconBack.setOnClickListener(this);

        mineShare= (ImageView) findViewById(R.id.mine_icon_krtv_share);//分享
        mineShare.setOnClickListener(this);

        personalName= (TextView) findViewById(R.id.personal_name);//名字
        personalName.setOnClickListener(this);

        focusCompany= (LinearLayout) findViewById(R.id.focus_company);//关注的公司
        focusCompany.setOnClickListener(this);

        personalImg= (ImageView) findViewById(R.id.mine_personal_img_0);//头像


    }

    @Override
    public void initData() {

        personalName.setText(getIntent().getStringExtra("name"));
        Picasso.with(this).load(getIntent().getStringExtra("avatarImg")).resize(200,200).transform(new PicassoCirclTransform()).into(personalImg);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_edit://编辑
                Intent intent=new Intent(this,AccountActivity.class );
                intent.putExtra("name", personalName.getText());
                startActivity(intent);
                break;
            case R.id.mine_icon_back://返回
                finish();
                break;
            case R.id.mine_icon_krtv_share://分享
                showShare();
                break;
            case R.id.personal_name://昵称
                personalName.getText();
                break;
            case R.id.focus_company://关注的公司
                Intent intent1=new Intent(this,FocusCompanyActivity.class);
                startActivity(intent1);
                break;

        }
    }
    //分享
    private void showShare() {
//        ShareSDK.initSDK(this);
//        OnekeyShare oks = new OnekeyShare();
//        oks.show(this);
    }

    // 设置用户
    public void setUser(){
        MyUser myUser = BmobUser.getCurrentUser(this, MyUser.class);
        BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username", myUser.getUsername());
        bmobQuery.findObjects(this, new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                RoundDrawable roundDrawable = new RoundDrawable(list.get(0).getImage());
                personalImg.setImageDrawable(roundDrawable);
//                personalImg.setImageBitmap(list.get(0).getImage());
                personalName.setText(list.get(0).getUsername());
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}

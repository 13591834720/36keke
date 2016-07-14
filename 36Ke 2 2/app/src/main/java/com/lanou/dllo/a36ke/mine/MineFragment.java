package com.lanou.dllo.a36ke.mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanou.dllo.a36ke.base.BaseFragment;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.mine.favorite.FavoriteActivity;
import com.lanou.dllo.a36ke.mine.login.LoginActivity;
import com.lanou.dllo.a36ke.liteorm.MyUser;
import com.lanou.dllo.a36ke.mine.order.OrderActivity;
import com.lanou.dllo.a36ke.mine.personinfo.PersonalAccountActivity;
import com.lanou.dllo.a36ke.mine.setting.MineSettingActivity;
import com.lanou.dllo.a36ke.tools.WebViewActivity;
import com.lanou.dllo.a36ke.tools.RoundDrawable;
import com.squareup.picasso.Picasso;

/**
 * Created by dllo on 16/6/17.
 * 我的-fragment
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout messageLayout, orderLayout;
    private RelativeLayout accountLayout, authenticationLayout, favoriteLayout, companyLayout, couponLayout, understandLayout;
    private ImageView mineSetting;
    private RelativeLayout call;
    private PopupWindow popupWindow;
    private LinearLayout mineLayout;
    private TextView brief;
    private LinearLayout minePopup;
    private RelativeLayout mineAccount;
    private MyUser user;
    private Bitmap bitmap;
    private String name;
    private ImageView headIv;
    private TextView nameTv;
    private LogInReceiver logInReceiver;
    private int REQUEST_CODE = 110;
    private boolean isFirstCLick = false;
    @Override
    public int setLayout() {
        return R.layout.fragment_mine;
    }
    @Override
    public void initView(View view) {
        messageLayout = (LinearLayout) view.findViewById(R.id.mine_message_layout);//我的消息
        messageLayout.setOnClickListener(this);
        orderLayout = (LinearLayout) view.findViewById(R.id.mine_order_layout);//我的订单
        orderLayout.setOnClickListener(this);
        accountLayout = (RelativeLayout) view.findViewById(R.id.mine_account_layout);//账号信息
        accountLayout.setOnClickListener(this);
        authenticationLayout = (RelativeLayout) view.findViewById(R.id.mine_authentication_layout);//跟投人认证
        authenticationLayout.setOnClickListener(this);
        favoriteLayout = (RelativeLayout) view.findViewById(R.id.mine_favorite_layout);//我收藏的文章
        favoriteLayout.setOnClickListener(this);
        companyLayout = (RelativeLayout) view.findViewById(R.id.mine_company_layout);//我投资的公司
        companyLayout.setOnClickListener(this);
        couponLayout = (RelativeLayout) view.findViewById(R.id.mine_coupon_layout);//我的投资劵
        couponLayout.setOnClickListener(this);
        understandLayout = (RelativeLayout) view.findViewById(R.id.mine_understand_layout);//了解股权投资
        understandLayout.setOnClickListener(this);
        mineSetting = (ImageView) view.findViewById(R.id.mine_setting);//设置
        mineSetting.setOnClickListener(this);
        call = (RelativeLayout) view.findViewById(R.id.mine_call_hotline);//客服热线
        call.setOnClickListener(this);
        mineAccount = (RelativeLayout) view.findViewById(R.id.mine_account_);//个人信息
        mineAccount.setOnClickListener(this);
        headIv = (ImageView) view.findViewById(R.id.mine_img);//头像
        nameTv = (TextView) view.findViewById(R.id.mine_name_tv);//名字
        mineLayout = (LinearLayout) view.findViewById(R.id.mine_layout);
        brief = (TextView) view.findViewById(R.id.mine_brief_fragment); // 简介
    }

    @Override
    public void initData() {
        user = MyUser.getCurrentUser(context, MyUser.class);
       // initReceiver();
        initLogIn();
        WeiBoReceiver weiBoReceiver = new WeiBoReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.lanou.dllo.a36ke.mine.login.WEB");
        context.registerReceiver(weiBoReceiver, filter);

        logInReceiver = new LogInReceiver();
        IntentFilter intentFilter = new IntentFilter("com.lanou.dllo.a36ke.SETHEAD");
        context.registerReceiver(logInReceiver, intentFilter);

        if (user != null) {
            getBrief();
        }
    }

    // 读取简介
    public void getBrief(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("brief", Context.MODE_PRIVATE);
        String mBrief = sharedPreferences.getString("brief", "空空如也");
        brief.setText(mBrief);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_account_://设置
                if (user == null) {
                    Intent intent_login= new Intent(context, LoginActivity.class);
                    startActivity(intent_login);
                }
                break;
            case R.id.mine_message_layout://我的消息
                if (user == null) {
                    Intent intent_login= new Intent(context, LoginActivity.class);
                    startActivity(intent_login);
                } else {
                    Intent intent = new Intent(context, MessageActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mine_order_layout://我的订单
                if (user == null) {
                    Intent intent_login = new Intent(context, LoginActivity.class);
                    startActivity(intent_login);
                } else {
                    Intent intent_order = new Intent(context, OrderActivity.class);
                    startActivity(intent_order);
                }
                break;
            case R.id.mine_account_layout://账号信息
                if (user == null) {
                    Intent intent_login = new Intent(context, LoginActivity.class);
                    startActivityForResult(intent_login, 100);
                } else {
                    Intent intent_account = new Intent(context, AccountActivity.class);
                    startActivity(intent_account );
                }
                break;
            case R.id.mine_authentication_layout://跟投人认证
                if (user == null) {
                    Intent intent_login = new Intent(context, LoginActivity.class);
                    startActivity(intent_login);
                } else {
                    Intent intent_authentic = new Intent(context, WebViewActivity.class);
                    intent_authentic.putExtra("webUrl", "https://z.36kr.com/m/investorValidate");
                    startActivity(intent_authentic);
                }
                break;
            case R.id.mine_favorite_layout://我的收藏
                if (user == null) {
                    Intent intent_login = new Intent(context, LoginActivity.class);
                    startActivity(intent_login);
                } else {
                    Intent intent_favorite = new Intent(context, FavoriteActivity.class);
                    startActivity(intent_favorite);
                }
                break;
            case R.id.mine_coupon_layout://我的投资劵
                if (user == null) {
                    Intent intent_login = new Intent(context, LoginActivity.class);
                    startActivity(intent_login);
                } else {
                    Intent intent_coupon = new Intent(context, WebViewActivity.class);
                    intent_coupon.putExtra("webUrl", "https://z.36kr.com/m/voucher/unused?ktm_medium=app");
                    startActivity(intent_coupon);
                }
                break;
            case R.id.mine_understand_layout://了解股权投资

                Intent intent_understand = new Intent(context, WebViewActivity.class);
                intent_understand.putExtra("webUrl", "https://z.36kr.com/m/class");
                startActivity(intent_understand);


                break;
            case R.id.mine_company_layout://我投资的公司
                if (user == null) {
                    Intent intent_login = new Intent(context, LoginActivity.class);
                    startActivity(intent_login);
                } else {
                    Intent intent_company = new Intent(context, CompanyActivity.class);
                    startActivityForResult(intent_company, REQUEST_CODE);
                }
                break;

            case R.id.mine_setting://设置
                if (user == null) {
                    Intent intent_login = new Intent(context, LoginActivity.class);
                    startActivity(intent_login);
                } else {
                    Intent intent_mine_setting = new Intent(context, MineSettingActivity.class);
                    startActivity(intent_mine_setting);
                }

                break;
            case R.id.mine_call_hotline://客服热线
                if (isFirstCLick) {
                    popupWindow.dismiss();
                    isFirstCLick = false;
                    break;
                } else {
                    settingPopup();
                    isFirstCLick = true;
                    break;
                }
        }
    }
    //客服热线popup
    public void settingPopup() {
        popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(context).inflate(R.layout.mine_popup_hotline, null);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        minePopup = (LinearLayout) view.findViewById(R.id.mine_popup);
        TextView callHot = (TextView) view.findViewById(R.id.mine_call);
        callHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:400-995-3636"));
                startActivity(callIntent);
            }
        });
        TextView cancel = (TextView) view.findViewById(R.id.mine_popou_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(mineLayout, Gravity.BOTTOM, 0, 0);
    }
//    private void initReceiver() {
//        logInReceiver = new LogInReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.lanou.dllo.a36ke.SETHEAD");
//        getActivity().registerReceiver(logInReceiver, filter);
//    }

    //登录
    public void initLogIn() {
        if (user != null) {
            name = user.getUsername();
            bitmap = user.getImage();
            if (bitmap != null) {
                headIv.setImageDrawable(new RoundDrawable(user.getImage()));
            } else {
                headIv.setImageResource(R.mipmap.common_avatar);
            }
            nameTv.setText(name);
        } else {
            nameTv.setText("未登录");
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        context.unregisterReceiver(logInReceiver);

    }


    // 登录后刷新数据
    class LogInReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            initData();
        }
    }

    class WeiBoReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Picasso.with(context).load(intent.getStringExtra("head")).into(headIv);
            nameTv.setText(intent.getStringExtra("name"));
        }
    }

}

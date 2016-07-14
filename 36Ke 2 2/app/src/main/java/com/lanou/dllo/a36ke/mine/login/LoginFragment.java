package com.lanou.dllo.a36ke.mine.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.dllo.a36ke.base.BaseFragment;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.base.MyApplication;
import com.lanou.dllo.a36ke.liteorm.ArticleBean;
import com.lanou.dllo.a36ke.liteorm.LiteOrmSingle;
import com.lanou.dllo.a36ke.liteorm.MyUser;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by dllo on 16/6/27.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private EditText nameEt;//输入手机号码
    private EditText psw;//输入密码
    private Button login;//登录按钮
    private TextView forgetPassword;//忘记密码
    private UserReceiver receiver;
    private ImageView shareQQ;//qq
    private ImageView shareWeibo;//微博
    private Platform weibo;
    private MyUser user;
    private Platform qq;


    @Override
    public int setLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView(View view) {
        nameEt = (EditText) view.findViewById(R.id.mine_name);
        psw = (EditText) view.findViewById(R.id.mine_psw);
        login = (Button) view.findViewById(R.id.mine_login);
        forgetPassword = (TextView) view.findViewById(R.id.mine_forget_password);
        login.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        //qq分享
        shareQQ = (ImageView) view.findViewById(R.id.share_qq);
        shareQQ.setOnClickListener(this);
        //微博分享
        shareWeibo = (ImageView) view.findViewById(R.id.share_weibo);
        shareWeibo.setOnClickListener(this);
    }

    @Override
    public void initData() {
        initUserReceiver();
    }

    //注册广播 :接受 注册页面传来的数据
    public void initUserReceiver() {
        receiver = new UserReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.lanou.dllo.a36ke.mine.login.USER");
        context.registerReceiver(receiver, filter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_login:
                initLogin();
                break;
            case R.id.mine_forget_password:
                break;
            case R.id.share_qq://qq登录
                qq = ShareSDK.getPlatform(QQ.NAME);
                qq.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        Intent intent2 = new Intent(context, CorrelationActivity.class);
                        intent2.putExtra("qqHead", qq.getDb().getUserIcon());
                        intent2.putExtra("qqName", qq.getDb().getUserName());
                        context.startActivity(intent2);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                    }
                });
                qq.authorize();
                break;
            case R.id.share_weibo://微博登录
                weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                weibo.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                    }
                });
                weibo.authorize();
                break;
        }
    }

    public void initLogin() {
        MyUser myUser = new MyUser();
        myUser.setUsername(nameEt.getText().toString());
        myUser.setPassword(psw.getText().toString());
        myUser.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                BmobQuery<ArticleBean> query = new BmobQuery<ArticleBean>();
                query.addWhereEqualTo("userName", BmobUser.getCurrentUser(MyApplication.getContext()).getUsername());//按用户名查询
                query.findObjects(context, new FindListener<ArticleBean>() {
                    @Override
                    public void onSuccess(List<ArticleBean> list) {
                        LiteOrmSingle.getLiteOrmSingle().getLiteOrm().deleteAll(ArticleBean.class);//清空本地数据库
                        for (ArticleBean articleBean : list) {
                            LiteOrmSingle.getLiteOrmSingle().getLiteOrm().insert(articleBean);//将查询结果加到数据库
                        }
                        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                        //通知<我的>页面的数据改变
                        Intent intent = new Intent("com.lanou.dllo.a36ke.SETHEAD");
                        context.sendBroadcast(intent);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(int i, String s) {
                    }
                });
                getActivity().finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //接受注册界面传来的用户名 密码
    class UserReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            nameEt.setText(intent.getStringExtra("name"));
            psw.setText(intent.getStringExtra("password"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(receiver);
    }
}

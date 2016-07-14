package com.lanou.dllo.a36ke.base;

import android.app.Application;
import android.content.Context;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by dllo on 16/6/22.
 */
public class MyApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        ShareSDK.initSDK(mContext);
        Bmob.initialize(this,"5c599776c63f46bc067252a66fc42061");
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this);
    }
    public static Context getContext() {
        return mContext;
    }
}

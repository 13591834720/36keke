package com.lanou.dllo.a36ke.tools;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.base.MyApplication;
import com.lanou.dllo.a36ke.main.MainActivity;

import cn.bmob.push.PushConstants;

/**
 * Created by dllo on 16/7/7.
 */
//推送
public class MyPushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.d("MyPushMessageReceiver", "ddddd");
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            Log.d("bmob", "客户端收到推送内容：" + intent.getStringExtra("msg"));
            // 第一步:获得一个manager对象
            NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            // 第二步:通过Builder来设置notification对象
            Notification.Builder builder = new Notification.Builder(context);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            // 设置大图标
            builder.setLargeIcon(bitmap);
            // 设置标题
            builder.setContentTitle("36氪");
            builder.setContentText(intent.getStringExtra("msg"));
            // 设置小图标
            builder.setSmallIcon(R.mipmap.ic_launcher);
            // 设置自动取消
            builder.setAutoCancel(true);
            Notification notification = builder.build();
            manager.notify(2016, notification);
        }
    }

}

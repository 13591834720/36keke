package com.lanou.dllo.a36ke.news;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.base.MyClickListener;
import com.lanou.dllo.a36ke.tools.PicassoCirclTransform;
import com.lanou.dllo.a36ke.tools.SingleRequest;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by dllo on 16/6/17.
 */
public class NewsAdapter extends BaseAdapter {
    private MyClickListener clickListener;

    public void setClickListener(MyClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private Bean bean;

    public void setBean(Bean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    private Context context;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return bean == null ? 0 : bean.getData().getData().size();
    }

    @Override
    public Object getItem(int position) {
        return bean.getData().getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        myViewHolder.name.setText(bean.getData().getData().get(position).getUser().getName());
        myViewHolder.titles.setText(bean.getData().getData().get(position).getTitle());
        myViewHolder.columnName.setText(bean.getData().getData().get(position).getColumnName());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        long pubTime = bean.getData().getData().get(position).getPublishTime();
        String time = simpleDateFormat.format(new Date(pubTime));
        myViewHolder.publishTime.setText(time);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(position);
            }
        });


        SingleRequest.getSingleRequest(context).getImageLoader().get(bean.getData().getData().get(position).getFeatureImg()
                , ImageLoader.getImageListener(myViewHolder.featureImg, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        Picasso.with(context).load(bean.getData().getData().get(position).getFeatureImg()).into(myViewHolder.featureImg);
        return convertView;
    }

    class MyViewHolder {
        TextView titles;
        TextView name;
        TextView publishTime;
        TextView columnName;
        ImageView featureImg;

        public MyViewHolder(View view) {
            titles = (TextView) view.findViewById(R.id.item_news_titles);
            name = (TextView) view.findViewById(R.id.item_news_author);
            publishTime = (TextView) view.findViewById(R.id.item_news_time);
            columnName = (TextView) view.findViewById(R.id.item_news_type);
            featureImg = (ImageView) view.findViewById(R.id.item_news_img);

        }


    }

//    public String twoDateDistance(Long startDate, Long endDate) {
//
//        if (startDate == null || endDate == null) {
//            return null;
//        }
//        long timeLong = endDate - startDate;
//        if (timeLong < 60 * 1000)
//            return timeLong / 1000 + "秒前";
//        else if (timeLong < 60 * 60 * 1000) {
//            timeLong = timeLong / 1000 / 60;
//            return timeLong + "分钟前";
//        } else if (timeLong < 60 * 60 * 24 * 1000) {
//            timeLong = timeLong / 60 / 60 / 1000;
//            return timeLong + "小时前";
//        }
//        else if (timeLong < 60 * 60 * 24 * 1000 * 7) {
//            timeLong = timeLong / 1000 / 60 / 60 / 24;
//            return timeLong + "天前";
//        } else if (timeLong < 60 * 60 * 24 * 1000 * 7 * 4) {
//            timeLong = timeLong / 1000 / 60 / 60 / 24 / 7;
//            return timeLong + "周前";
//        } else {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
//            return sdf.format(startDate);
//        }
//     }
}

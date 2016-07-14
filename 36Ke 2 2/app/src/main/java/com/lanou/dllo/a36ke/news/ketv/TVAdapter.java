package com.lanou.dllo.a36ke.news.ketv;

import android.net.Uri;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.lanou.dllo.a36ke.R;
import com.squareup.picasso.Picasso;


/**
 * Created by dllo on 16/6/21.
 * 解析TVBean时绑定的适配器
 */
public class TVAdapter extends BaseAdapter {
    private TVBean tvBean;
    private Context context;
    private MyTvViewHolder myTvViewHolder = null;

    public TVAdapter(Context context) {
        this.context = context;
    }
    public void setTvBean(TVBean tvBean) {
        this.tvBean = tvBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tvBean == null ? 0 : tvBean.getData().getData().size();
    }

    @Override
    public Object getItem(int position) {
        return tvBean.getData().getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyTvViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_news_tv, parent, false);
            holder = new MyTvViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyTvViewHolder) convertView.getTag();
        }
        holder.newTvVideo.setVideoURI(Uri.parse(tvBean.getData().getData().get(position).getTv().getVideoSource()));
        holder.newTvVideo.setMediaController(new android.widget.MediaController(context));
        holder.newTvVideo.pause();
        Picasso.with(context).load(tvBean.getData().getData().get(position).getTv().getFeatureImg()).into(holder.tvImg);
        holder.tvTitle.setText(tvBean.getData().getData().get(position).getTv().getTitle());
        final MyTvViewHolder finalMyViewHolder = holder;
        holder.tvImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myTvViewHolder != null) {
                    myTvViewHolder.newTvVideo.pause();
                    myTvViewHolder.tvImg.setVisibility(View.VISIBLE);
                    myTvViewHolder.newTvVideo.setVisibility(View.GONE);
                    myTvViewHolder.tvTitle.setVisibility(View.VISIBLE);
                }
                finalMyViewHolder.newTvVideo.setVisibility(View.VISIBLE);
                finalMyViewHolder.tvImg.setVisibility(View.GONE);
                finalMyViewHolder.tvTitle.setVisibility(View.GONE);
                finalMyViewHolder.newTvVideo.start();
                myTvViewHolder = finalMyViewHolder;
            }
        });

        return convertView;
    }

    class MyTvViewHolder {
        VideoView newTvVideo;
        TextView tvTitle;
        ImageView tvImg;

        public MyTvViewHolder(View view) {
            newTvVideo = (VideoView) view.findViewById(R.id.news_tv_video);
            tvTitle = (TextView) view.findViewById(R.id.tv_title_tv);
            tvImg= (ImageView) view.findViewById(R.id.tv_image_iv);
        }
    }
}


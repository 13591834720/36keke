package com.lanou.dllo.a36ke.discovery.college36ke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.tools.SingleRequest;

/**
 * Created by dllo on 16/6/28.
 */
public class CollegeAdapter extends BaseAdapter {

    private CollegeBean bean;

    public void setBean(CollegeBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    private Context context;

    public CollegeAdapter(Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dis_college, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.name.setText(bean.getData().getData().get(position).getUser().getName());
        myViewHolder.titles.setText(bean.getData().getData().get(position).getTitle());
        //myViewHolder.time.setText(bean.getData().getData().get(position).getPublishTime());

        SingleRequest.getSingleRequest(context).getImageLoader().get(bean.getData().getData().get(position).getFeatureImg()
                , ImageLoader.getImageListener(myViewHolder.featureImg, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        return convertView;
    }

    class MyViewHolder {

        TextView titles;
        TextView name;
        TextView time;
        ImageView featureImg;

        public MyViewHolder(View view) {
            titles = (TextView) view.findViewById(R.id.dis_college_titles);
            name = (TextView) view.findViewById(R.id.dis_college_author);
            time = (TextView) view.findViewById(R.id.dis_college_time);
            featureImg = (ImageView) view.findViewById(R.id.dis_college_featureImg);

        }
    }
}

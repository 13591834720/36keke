package com.lanou.dllo.a36ke.discovery.findinvestors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.tools.PicassoCirclTransform;
import com.squareup.picasso.Picasso;

/**
 * Created by dllo on 16/6/23.
 */
public class FindAdapter extends BaseAdapter {
    private FindBean bean;


    public void setBean(FindBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    private Context context;

    public FindAdapter(Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dis_find, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);

        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        if(bean.getData().getData().get(position).getFocusIndustry().size()==3){
            myViewHolder.focusIndustry.setText("关注领域:"+bean.getData().getData().get(position).getFocusIndustry().get(0)+" "+bean.getData().getData().get(position).getFocusIndustry().get(1)+" "+bean.getData().getData().get(position).getFocusIndustry().get(2));
        } else if(bean.getData().getData().get(position).getFocusIndustry().size()==2){
            myViewHolder.focusIndustry.setText("关注领域:"+bean.getData().getData().get(position).getFocusIndustry().get(0)+" "+bean.getData().getData().get(position).getFocusIndustry().get(1));
        }else if(bean.getData().getData().get(position).getFocusIndustry().size()==1){
            myViewHolder.focusIndustry.setText("关注领域:"+bean.getData().getData().get(position).getFocusIndustry().get(0));
        }else if(bean.getData().getData().get(position).getFocusIndustry().size()==0) {
            myViewHolder.focusIndustry.setText("关注领域:" + "未纰漏");
        }
        if(bean.getData().getData().get(position).getInvestPhases().size()==3){
            myViewHolder.investPhases.setText("投资阶段:"+bean.getData().getData().get(position).getInvestPhases().get(0)+" "+bean.getData().getData().get(position).getInvestPhases().get(1)+" "+bean.getData().getData().get(position).getInvestPhases().get(2));
        } else if(bean.getData().getData().get(position).getInvestPhases().size()==2){
           myViewHolder.investPhases.setText("投资阶段:"+bean.getData().getData().get(position).getInvestPhases().get(0)+" "+bean.getData().getData().get(position).getInvestPhases().get(1));
        }else if(bean.getData().getData().get(position).getFocusIndustry().size()==1){
            myViewHolder.investPhases.setText("投资阶段"+bean.getData().getData().get(position).getInvestPhases().get(0));
        }else if(bean.getData().getData().get(position).getInvestPhases().size()==0) {
            myViewHolder.investPhases.setText("投资阶段" + "未纰漏");
        }
        myViewHolder.name.setText(bean.getData().getData().get(position).getUser().getName());
        Picasso.with(context).load(bean.getData().getData().get(position).getUser().getAvatar()).resize(100, 100).
                transform(new PicassoCirclTransform()).into(myViewHolder.avatarImg);
        return convertView;
    }

    class MyViewHolder {
        ImageView avatarImg;
        TextView name;
        TextView focusIndustry;
        TextView investPhases;

        public MyViewHolder(View view) {
            avatarImg = (ImageView) view.findViewById(R.id.find_avatar);
            name = (TextView) view.findViewById(R.id.find_name);
            focusIndustry = (TextView) view.findViewById(R.id.find_focus_industry);
            investPhases = (TextView) view.findViewById(R.id.find_invest_phases);

        }
    }
}

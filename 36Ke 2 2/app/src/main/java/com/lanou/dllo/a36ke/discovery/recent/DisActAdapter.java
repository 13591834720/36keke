package com.lanou.dllo.a36ke.discovery.recent;

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
import com.squareup.picasso.Picasso;

/**
 * Created by dllo on 16/6/23.
 */
public class DisActAdapter extends BaseAdapter {
    private DisActBean bean;

    public void setBean(DisActBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    private Context context;

    public DisActAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return bean==null?0:bean.getData().getData().size();
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
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_dis_detail,parent,false);
            myViewHolder=new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        }else {
            myViewHolder= (MyViewHolder) convertView.getTag();
        }
        myViewHolder.disContent.setText(bean.getData().getData().get(position).getActivityName());
        myViewHolder.disCity.setText(bean.getData().getData().get(position).getActivityCity());
        myViewHolder.disStatus.setText(bean.getData().getData().get(position).getActivityStatus());
        myViewHolder.disDesc.setText(bean.getData().getData().get(position).getActivityDesc());
        myViewHolder.disTime.setText(bean.getData().getData().get(position).getActivityTime());
        Picasso.with(context).load(bean.getData().getData().get(position).getActivityImg()).into(myViewHolder.activityImg);
        return convertView;
    }
    class  MyViewHolder{
        ImageView activityImg;
        TextView disContent;
        TextView disStatus;
        TextView disCity;
        TextView disTime;
        TextView disDesc;
        public MyViewHolder(View view){
            activityImg= (ImageView) view.findViewById(R.id.dis_activityImg);
            disContent= (TextView) view.findViewById(R.id.item_dis_content);
            disStatus= (TextView) view.findViewById(R.id.item_dis_status);
            disCity= (TextView) view.findViewById(R.id.item_dis_city);
            disTime= (TextView) view.findViewById(R.id.item_dis_time);
            disDesc= (TextView) view.findViewById(R.id.item_dis_desc);

        }


    }
}

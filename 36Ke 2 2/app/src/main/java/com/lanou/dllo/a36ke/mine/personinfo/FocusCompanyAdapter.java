package com.lanou.dllo.a36ke.mine.personinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.dllo.a36ke.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/7/5.
 */
public class FocusCompanyAdapter extends BaseAdapter {
    private List<InfoBean> infoBean;

    public void setInfoBean(List<InfoBean> infoBean) {
        this.infoBean = infoBean;
        notifyDataSetChanged();
    }

    private Context context;

    public FocusCompanyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return infoBean == null?0:infoBean.size();
    }

    @Override
    public Object getItem(int position) {
        return infoBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_mine_focus_company,parent,false);
            myViewHolder=new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        }else {
            myViewHolder= (MyViewHolder) convertView.getTag();
        }
        myViewHolder.name.setText(infoBean.get(position).getCompanyName());
        myViewHolder.brief.setText(infoBean.get(position).getCompanyBrief());

        return convertView;
    }
    class MyViewHolder{
        ImageView logo;
        TextView name;
        TextView brief;
        public MyViewHolder(View view){
            logo= (ImageView) view.findViewById(R.id.item_focus_company_logo);
            name= (TextView) view.findViewById(R.id.item_focus_company_name);
            brief= (TextView) view.findViewById(R.id.item_focus_company_brief);
        }

    }
}

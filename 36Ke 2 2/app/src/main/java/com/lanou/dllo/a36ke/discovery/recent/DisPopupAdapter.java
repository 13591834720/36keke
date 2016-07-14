package com.lanou.dllo.a36ke.discovery.recent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lanou.dllo.a36ke.base.MyClickListener;
import com.lanou.dllo.a36ke.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/24.
 */
public class DisPopupAdapter extends BaseAdapter {
    private ArrayList<String>bean;
    private MyClickListener clickListener;

    public void setClickListener(MyClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setBean(ArrayList<String> bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    private Context context;

    public DisPopupAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return bean!=null&&bean.size()>0?bean.size():0;
    }

    @Override
    public Object getItem(int position) {
        return bean!=null&&bean.size()>0?bean.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_dis_popup,parent,false);
            myViewHolder=new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        }else {
            myViewHolder= (MyViewHolder) convertView.getTag();
        }
        myViewHolder.textViewPopup.setText(bean.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(position);
            }
        });
        return convertView;
    }
    class  MyViewHolder{
        TextView textViewPopup;
        private MyViewHolder(View view){
            textViewPopup= (TextView) view.findViewById(R.id.dis_popup_all);

        }
    }
}

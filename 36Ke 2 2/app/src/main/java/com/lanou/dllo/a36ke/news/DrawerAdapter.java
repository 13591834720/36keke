package com.lanou.dllo.a36ke.news;

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
 * Created by dllo on 16/6/20.
 */
public class DrawerAdapter extends BaseAdapter {
    private ArrayList<String> bean;

    public void setClickListener(MyClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private MyClickListener clickListener;

    public void setBean(ArrayList<String> bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    private Context context;


    public DrawerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return bean == null ? 0 : bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.menu, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.newsMenu.setText(bean.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(position);
            }
        });
        return convertView;
    }
    class MyViewHolder {
        TextView newsMenu;
        public MyViewHolder(View view) {
            newsMenu = (TextView) view.findViewById(R.id.item_news_menu);
        }
    }
}

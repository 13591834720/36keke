package com.lanou.dllo.a36ke.mine.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.liteorm.ArticleBean;
import com.lanou.dllo.a36ke.tools.PicassoCirclTransform;
import com.lanou.dllo.a36ke.tools.SingleRequest;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dllo on 16/7/2.
 */
public class FavoriteAdapter extends BaseAdapter {
    private List<ArticleBean> articleBeans;
    private Context context;
    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setArticleBeans(List<ArticleBean> articleBeans) {
        this.articleBeans = articleBeans;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return articleBeans == null ? 0 :articleBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return articleBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.name.setText(articleBeans.get(position).getName());
        myViewHolder.titles.setText(articleBeans.get(position).getTitle());
        myViewHolder.columnName.setText(articleBeans.get(position).getColumnName());
       Picasso.with(context).load(articleBeans.get(position).getFeatureImg()).into(myViewHolder.featureImg);

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
}

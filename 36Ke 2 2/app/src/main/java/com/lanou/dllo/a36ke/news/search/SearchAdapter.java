package com.lanou.dllo.a36ke.news.search;

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
 * Created by dllo on 16/6/27.
 */
public class SearchAdapter extends BaseAdapter {
    private SearchBean searchBean;

    public void setSearchBean(SearchBean searchBean) {
        this.searchBean = searchBean;
        notifyDataSetChanged();
    }

    private Context context;

    public SearchAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return searchBean==null?0:searchBean.getData().getData().size();
    }

    @Override
    public Object getItem(int position) {
        return searchBean.getData().getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_news_search,null);
            myViewHolder=new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        }
        else {
            myViewHolder= (MyViewHolder) convertView.getTag();
        }
        myViewHolder.titles.setText(searchBean.getData().getData().get(position).getTitle());
        myViewHolder.author.setText(searchBean.getData().getData().get(position).getUser().getName());
        myViewHolder.type.setText(searchBean.getData().getData().get(position).getColumnName());
        SingleRequest.getSingleRequest(context).getImageLoader().get(searchBean.getData().getData().get(position).getFeatureImg(),
                ImageLoader.getImageListener(myViewHolder.searchImg,R.mipmap.ic_launcher,R.mipmap.ic_launcher));


        return convertView;
    }
    class  MyViewHolder{
        ImageView searchImg;
        TextView  titles;
        TextView author;
        TextView type;
        public MyViewHolder(View view) {
            searchImg= (ImageView) view.findViewById(R.id.news_img);
            titles= (TextView) view.findViewById(R.id.news_search_titles);
            author= (TextView) view.findViewById(R.id.news_search_author);
            type= (TextView) view.findViewById(R.id.news_search_type);

        }
    }
}

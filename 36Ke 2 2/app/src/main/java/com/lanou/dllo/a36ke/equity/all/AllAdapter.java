package com.lanou.dllo.a36ke.equity.all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lanou.dllo.a36ke.base.MyApplication;
import com.lanou.dllo.a36ke.base.MyClickListener;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.mine.personinfo.InfoBean;
import com.squareup.picasso.Picasso;

/**
 * Created by dllo on 16/6/21.
 */
public class AllAdapter extends BaseAdapter {
    private AllBean allBeen;

    private MyClickListener myClickListener;
    private ClickListener clickListener;

    public void setYouClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private InfoBean infoBean;

    public void setInfoBean(InfoBean infoBean) {
        this.infoBean = infoBean;
        notifyDataSetChanged();
    }

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void setAllBeen(AllBean allBeen) {
        this.allBeen = allBeen;
        notifyDataSetChanged();
    }
    public void addBean(AllBean allBean){
        if (this.allBeen==null){
            this.allBeen=allBean;
        }else {
            this.allBeen.getData().getData().addAll(allBean.getData().getData());
        }
        notifyDataSetChanged();
    }
    private Context context;
    public AllAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return allBeen == null ? 0 : allBeen.getData().getData().size();
    }
    @Override
    public Object getItem(int position) {
        return allBeen.getData().getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_equity_all, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);

        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        myViewHolder.brief.setText(allBeen.getData().getData().get(position).getCompany_brief());
        myViewHolder.leadName.setText(allBeen.getData().getData().get(position).getLead_name());
        myViewHolder.name.setText(allBeen.getData().getData().get(position).getCompany_name());
        myViewHolder.adname.setText(allBeen.getData().getData().get(position).getCf_advantage().get(0).getAdname());
        myViewHolder.adcontent.setText(allBeen.getData().getData().get(position).getCf_advantage().get(0).getAdcontent());
        myViewHolder.member.setText(allBeen.getData().getData().get(position).getCf_advantage().get(1).getAdname());
        myViewHolder.member_adcontent.setText(allBeen.getData().getData().get(position).getCf_advantage().get(1).getAdcontent());
        int rate= (int) (allBeen.getData().getData().get(position).getRate()*100);
        myViewHolder.rate.setText(String.valueOf(rate));
        myViewHolder.progressBar.setProgress(rate);
        myViewHolder.desc.setText(allBeen.getData().getData().get(position).getFundStatus().getDesc());

        Picasso.with(MyApplication.getContext()).load(allBeen.getData().getData().get(position).getCompany_logo())
                .resize(100, 100).into(myViewHolder.logo);
        Picasso.with(MyApplication.getContext()).load(allBeen.getData().getData().get(position).getFile_list_img()).into(myViewHolder.listImg);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onIntentClick(position);
            }
        });
//        //关注
//        myViewHolder.allFocus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if(isChecked){
//                    myClickListener.onClick(position);
//                }else {
//                    infoBean.delete(context, new DeleteListener() {
//                        @Override
//                        public void onSuccess() {
//                            Toast.makeText(context, "取消关注", Toast.LENGTH_SHORT).show();
//                        }
//                        @Override
//                        public void onFailure(int i, String s) {
//                        }
//                    });
//                }
           // }
       // });
        return convertView;
    }



    class MyViewHolder {
        ImageView logo;
        TextView name;
        TextView brief;
        ImageView listImg;
        TextView leadName;
        TextView adname;
        TextView adcontent;
        TextView member;
        TextView member_adcontent;
        TextView rate;
        ProgressBar progressBar;
        TextView desc;
        CheckBox allFocus;//关注
        public MyViewHolder(View view) {
            logo = (ImageView) view.findViewById(R.id.all_logo);
            name = (TextView) view.findViewById(R.id.all_name);
            brief = (TextView) view.findViewById(R.id.all_brief);
            listImg = (ImageView) view.findViewById(R.id.list_Img);
            leadName = (TextView) view.findViewById(R.id.all_lead_name);
            adname = (TextView) view.findViewById(R.id.adnameTv);
            adcontent = (TextView) view.findViewById(R.id.adcontentTv);
            member = (TextView) view.findViewById(R.id.memberTV);
            member_adcontent = (TextView) view.findViewById(R.id.adcontent1Tv);
            rate= (TextView) view.findViewById(R.id.rate);
            progressBar = (ProgressBar) view.findViewById(R.id.all_raising_progress_bar);
            desc= (TextView) view.findViewById(R.id.desc);
//            allFocus= (CheckBox) view.findViewById(R.id.all_focus);
        }
    }
}

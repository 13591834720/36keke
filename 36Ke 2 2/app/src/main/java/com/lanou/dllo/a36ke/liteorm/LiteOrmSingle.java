package com.lanou.dllo.a36ke.liteorm;

import android.content.Context;

import com.lanou.dllo.a36ke.base.MyApplication;
import com.litesuits.orm.LiteOrm;

/**
 * Created by dllo on 16/6/29.
 */
public class LiteOrmSingle {
    private static LiteOrm liteOrm;
    private Context context;
    private ArticleBean articleBean;
    public ArticleBean getArticleBean() {
        if(articleBean == null){
            articleBean = new ArticleBean();
        }
        return articleBean;
    }

    private static LiteOrmSingle liteOrmSingle = new LiteOrmSingle();
    public static LiteOrmSingle getLiteOrmSingle(){
        return liteOrmSingle;
    }
    private LiteOrmSingle() {
        context = MyApplication.getContext();
    }

    public  LiteOrm getLiteOrm() {
        if(liteOrm==null){
            liteOrm = LiteOrm.newCascadeInstance(context,"dbName");
        }
        return liteOrm;
    }
}


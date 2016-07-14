package com.lanou.dllo.a36ke.liteorm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lanou.dllo.a36ke.liteorm.ArticleBean;

import java.io.ByteArrayOutputStream;

import cn.bmob.v3.BmobUser;

/**
 * Created by dllo on 16/6/30.
 * 表的数据类
 */
public class MyUser extends BmobUser {
    private ArticleBean articleBean;
    private String nickName;
    private String intro;
    private String thirdName;
    private String userPassword;

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public ArticleBean getArticleBean() {
        return articleBean;
    }

    public void setArticleBean(ArticleBean articleBean) {
        this.articleBean = articleBean;
    }


    private byte[] photo;

    public Bitmap getImage() {
        if (photo == null) {
            return null;
        }
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        return imageBitmap;
    }

    public void setImage(Bitmap image) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, output);
        photo = output.toByteArray();//转换成功了
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

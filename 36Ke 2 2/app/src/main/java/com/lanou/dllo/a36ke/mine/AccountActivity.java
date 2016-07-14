package com.lanou.dllo.a36ke.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.dllo.a36ke.base.BaseActivity;
import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.liteorm.MyUser;
import com.lanou.dllo.a36ke.main.MainActivity;
import com.lanou.dllo.a36ke.mine.setting.BriefActivity;
import com.lanou.dllo.a36ke.tools.RoundDrawable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by dllo on 16/6/25.
 * 我的-账号信息
 */
public class AccountActivity extends BaseActivity implements View.OnClickListener {
    private PopupWindow popupWindow;//
    private ImageView accountPicture;//头像
    private LinearLayout accountLayout;
    private LinearLayout mineBrief;//简介

    private static final String IMAGE_UNSPECIFIED = "image/*";//获取图片格式
    private static final int ALBUM_REQUEST_CODE = 1;//设置 图片使用码
    private static final int CAMERA_REQUEST_CODE = 2;// 相机使用码
    private static final int CROP_REQUEST_CODE = 4;//照片裁剪码
    private static final String TAG = "MyActivity";

    private MyUser myUser;
    private EditText nickNameEt;//用户昵称
    private EditText realName;//真实姓名
    private ImageView mineBack;//返回
    private TextView briefTv;//简介
    private final int RESULT_CODE = 100;
    private TextView mineTitle;

    @Override
    public int initLayout() {
        return R.layout.mine_account;
    }
    @Override
    public void initView() {
        accountPicture = (ImageView) findViewById(R.id.mine_account_picture);//头像
        accountPicture.setOnClickListener(this);
        accountLayout = (LinearLayout) findViewById(R.id.account_layout);
        mineBrief = (LinearLayout) findViewById(R.id.mine_brief);//简介
        mineBrief.setOnClickListener(this);
        nickNameEt = (EditText) findViewById(R.id.nick_name_et);//用户昵称
        realName = (EditText) findViewById(R.id.real_name_et);//真实姓名
        mineBack = (ImageView) findViewById(R.id.mine_back);//返回
        mineBack.setOnClickListener(this);
        briefTv = (TextView) findViewById(R.id.mine_brief_tv);//简介
        mineTitle= (TextView) findViewById(R.id.mine_title);
        mineTitle.setText("账号信息");
    }

    @Override
    public void initData() {
        realName.setText(getIntent().getStringExtra("name"));
        getUser();
        getBrief();
    }

    // 读取简介
    public void getBrief(){
        SharedPreferences sharedPreferences = getSharedPreferences("brief", MODE_PRIVATE);
        String brief = sharedPreferences.getString("brief", "空空如也");
        briefTv.setText(brief);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_account_picture://头像
                settingPopup();
                break;
            case R.id.mine_brief://简介
                Intent intent = new Intent(this, BriefActivity.class);
                startActivityForResult(intent,100);
                break;
            case R.id.mine_back://返回
                updateUser();
                finish();
                break;
        }
    }
    //设置照片popup
    public void settingPopup() {
        popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(this).inflate(R.layout.mine_account_popup, null);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        final TextView album = (TextView) view.findViewById(R.id.mine_account_album);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Intent.ACTION_PICK, null);
                intent3.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                startActivityForResult(intent3, ALBUM_REQUEST_CODE);
            }

        });
        TextView cancel = (TextView) view.findViewById(R.id.mine_account_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(accountLayout, Gravity.BOTTOM, 0, 0);
    }

    //设置头像图片
    private void getUser() {
        myUser = BmobUser.getCurrentUser(this, MyUser.class);
        if (myUser != null) {
            BmobQuery<MyUser> query = new BmobQuery<>();
            query.addWhereEqualTo("username", myUser.getUsername());
            query.findObjects(this, new FindListener<MyUser>() {
                @Override
                public void onSuccess(List<MyUser> list) {
                    realName.setText(myUser.getUsername());
                    nickNameEt.setText(myUser.getNickName());
                    if (myUser.getImage() != null) {
                        accountPicture.setImageDrawable(new RoundDrawable(myUser.getImage()));
                    }
                }
                @Override
                public void onError(int i, String s) {
                }
            });
        }
    }
    //更新数据
    private void updateUser() {
        if (myUser != null) {
            myUser.setNickName(nickNameEt.getText().toString());
            //user.setIntro(introEt.getText().toString());
            BmobUser bmobUser = BmobUser.getCurrentUser(this);
            myUser.update(this, bmobUser.getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    //通知登录界面数据改变
                    Intent intent = new Intent("com.lanou.dllo.a36ke.SETHEAD");
                    sendBroadcast(intent);
                }
                @Override
                public void onFailure(int i, String s) {
                }
            });
        }
    }
    //获取系统相册
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ALBUM_REQUEST_CODE:
                Log.i(TAG, "相册，开始裁剪");
                Log.i(TAG, "相册 [ " + data + " ]");
                if (data == null) {
                    return;
                }
                startCrop(data.getData());
                break;
            case CAMERA_REQUEST_CODE:
                File picture = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
                startCrop(Uri.fromFile(picture));
                break;
            case CROP_REQUEST_CODE:
                Log.i(TAG, "相册裁剪成功");
                Log.i(TAG, "裁剪以后 [ " + data + " ]");
                if (data == null) {
                    // TODO 如果之前以后有设置过显示之前设置的图片 否则显示默认的图片
                    return;
                }
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)压缩文件
                    accountPicture.setImageDrawable(new RoundDrawable(photo)); // 把图片显示在ImageView控件上
                    myUser.setImage(photo);
                }
                break;
            case 100:
                getBrief();//获得简介内容
                break;
        }
    }
    //修剪图片
    private void startCrop(Uri data) {
        Intent intent = new Intent("com.android.camera.action.CROP");// 调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(data, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");// 进行修剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }
    //销毁popup
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        updateUser();
    }
}

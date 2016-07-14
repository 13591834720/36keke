package com.lanou.dllo.a36ke.mine.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lanou.dllo.a36ke.base.BaseFragment;
import com.lanou.dllo.a36ke.R;

/**
 * Created by dllo on 16/6/27.
 */
public class RegisterFragment  extends BaseFragment implements View.OnClickListener {
    private EditText  registerPhoneNum;
    private Button mineObtain;
    @Override
    public int setLayout() {
        return R.layout.fragment_register;
    }

    @Override
    public void initView(View view) {
        registerPhoneNum= (EditText) view.findViewById(R.id.mine_register_phone_num);
        mineObtain= (Button) view.findViewById(R.id.mine_obtain);
        mineObtain.setOnClickListener(this);


    }

    @Override
    public void initData() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_obtain:
                if ("".equals(registerPhoneNum.getText().toString().trim())){
                    Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(context,SettingPswActivity.class);
                    intent.putExtra("phoneNumber",registerPhoneNum.getText().toString());
                    startActivity(intent);
                }
                break;
        }
    }

}

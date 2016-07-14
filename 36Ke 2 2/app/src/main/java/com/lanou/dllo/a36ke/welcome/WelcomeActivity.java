package com.lanou.dllo.a36ke.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.lanou.dllo.a36ke.R;
import com.lanou.dllo.a36ke.main.MainActivity;

/**
 * Created by dllo on 16/6/17.
 */
public class WelcomeActivity extends Activity{
    private TextView time;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        time= (TextView) findViewById(R.id.welcome_time);

        countDownTimer=new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText(String.valueOf(millisUntilFinished/1000));
            }
            @Override
            public void onFinish() {
                Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
              finish();
            }
        }.start();




    }
}


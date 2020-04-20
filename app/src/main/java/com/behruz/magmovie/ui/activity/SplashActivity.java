package com.behruz.magmovie.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.behruz.magmovie.R;
import com.behruz.magmovie.ui.activity.onBoarding.OnBoardingActivity;
import com.behruz.magmovie.utils.PreferenUtil.PreferenUtil;


public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PreferenUtil preferenUtil = PreferenUtil.getInstant(getApplicationContext());
                if (!preferenUtil.checkSkipLanguage()){
                    Intent intent = new Intent(SplashActivity.this, OnBoardingActivity.class);
                    SplashActivity.this.startActivity(intent);
                    SplashActivity.this.finish();
                }else {
                    Intent intents = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intents);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}


package com.abhishek.taskscheduler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AbstractBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent intent;
                if (!isLoggedIn()) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, HomeScreen.class);
                }
                startActivity(intent);
            }
        }, 2000);
    }

    private boolean isLoggedIn() {
        SharedPreferences mPrefs = getSharedPreferences("TS", MODE_PRIVATE);
        return mPrefs.getBoolean("LoggedIn", false);
    }

    @Override
    public void onPermissionResult(int requestCode, boolean isGranted, Object extras) {

    }
}

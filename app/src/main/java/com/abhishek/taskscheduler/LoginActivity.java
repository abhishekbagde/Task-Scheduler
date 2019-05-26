package com.abhishek.taskscheduler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AbstractBaseActivity {
    private EditText et_user_name;
    private EditText et_pwd;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_user_name.getText().toString().trim().equals("admin")
                        && et_pwd.getText().toString().trim().equals("admin")) {
                    Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                    saveLoggedIn();
                    finish();
                    Intent i = new Intent(LoginActivity.this, HomeScreen.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong user name or password. Please re-enter", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveLoggedIn() {
        SharedPreferences mPrefs = getSharedPreferences("TS", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putBoolean("LoggedIn", true);
        prefsEditor.commit();
    }

    @Override
    public void onPermissionResult(int requestCode, boolean isGranted, Object extras) {

    }
}

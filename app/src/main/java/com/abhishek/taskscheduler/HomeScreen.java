package com.abhishek.taskscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.abhishek.taskscheduler.payment.PaymentActivity;
import com.abhishek.taskscheduler.task.TaskActivity;
import com.abhishek.taskscheduler.timetable.TimeTableActivity;

public class HomeScreen extends AppCompatActivity {

    private LinearLayout ll_timetable;
    private LinearLayout ll_task;
    private LinearLayout ll_payment;
    private LinearLayout ll_aboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ll_timetable = (LinearLayout) findViewById(R.id.ll_timetable);
        ll_task = (LinearLayout) findViewById(R.id.ll_task);
        ll_payment = (LinearLayout) findViewById(R.id.ll_payment);
        ll_aboutUs = (LinearLayout) findViewById(R.id.ll_aboutUs);

        ll_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this, TimeTableActivity.class);
                startActivity(i);

            }
        });

        ll_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this, TaskActivity.class);
                startActivity(i);
            }
        });

        ll_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this, PaymentActivity.class);
                startActivity(i);
            }
        });

        ll_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this, AboutUsActivity.class);
                startActivity(i);
            }
        });

    }
}

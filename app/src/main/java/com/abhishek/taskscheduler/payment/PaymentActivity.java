package com.abhishek.taskscheduler.payment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.abhishek.taskscheduler.R;
import com.abhishek.taskscheduler.adapters.PaymentAdapter;
import com.abhishek.taskscheduler.models.TaskModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    private ListView lv_payment;

    private ArrayList<TaskModel> paymentList;
    private PaymentAdapter paymentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentList = getPaymentList();
        setContentView(R.layout.activity_payment);
        lv_payment = (ListView) findViewById(R.id.lv_payment);

        paymentAdapter = new PaymentAdapter(this, paymentList);
        lv_payment.setAdapter(paymentAdapter);
    }

    public ArrayList<TaskModel> getPaymentList() {
        ArrayList<TaskModel> paymentsList = new ArrayList<>();
        SharedPreferences mPrefs = getSharedPreferences("TS", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("PaymentList", "");
        if (!json.isEmpty()) {
            paymentsList = gson.fromJson(json, new TypeToken<List<TaskModel>>() {
            }.getType());
        }

        //Add default bills task if no other found in list
        if (paymentsList.isEmpty()) {
            String[] defaultPayments = {"Electric Bill", "Phone Bill", "Dish Recharge", "Water bill", "EMI"};
            for (int i = 0; i < defaultPayments.length; i++) {
                TaskModel taskModel = new TaskModel();
                taskModel.setTaskDateTimeInMillis(-1);
                taskModel.setTaskName(defaultPayments[i]);
                paymentsList.add(taskModel);
            }
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            json = gson.toJson(paymentsList); // myObject - instance of MyObject
            prefsEditor.putString("PaymentList", json);
            prefsEditor.commit();
        }
        return paymentsList;
    }
}

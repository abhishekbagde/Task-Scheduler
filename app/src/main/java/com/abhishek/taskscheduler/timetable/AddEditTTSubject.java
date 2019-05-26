package com.abhishek.taskscheduler.timetable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.taskscheduler.AbstractBaseActivity;
import com.abhishek.taskscheduler.R;
import com.abhishek.taskscheduler.models.TTSubject;
import com.abhishek.taskscheduler.receiver.AlarmReceiver;
import com.google.gson.Gson;

import java.util.Calendar;

public class AddEditTTSubject extends AbstractBaseActivity {
    private TTSubject ttSubject;

    private TextView tv_title;
    private EditText et_subject_name;
    private EditText et_teacher_name;
    private EditText et_additional_notes;
    private Button btn_done;

    boolean isNewEvent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ttSubject = (TTSubject) getIntent().getSerializableExtra("subject");

        setContentView(R.layout.activity_add_edit_ttsubject);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("Add/Edit Subject for " + ttSubject.getDay().getName() + " " + ttSubject.getTime().getName());

        if (ttSubject.getSubjectName().toString().trim().length() == 0) {
            isNewEvent = true;
        }

        et_subject_name = (EditText) findViewById(R.id.et_subject_name);
        et_teacher_name = (EditText) findViewById(R.id.et_teacher_name);
        et_additional_notes = (EditText) findViewById(R.id.et_additional_notes);

        et_subject_name.setText(ttSubject.getSubjectName());
        et_teacher_name.setText(ttSubject.getSubjectTeacher());
        et_additional_notes.setText(ttSubject.getAdditionNotes());

        btn_done = (Button) findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    save();
                }
            }
        });

    }

    private boolean validate() {
        if (et_subject_name.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Please enter subject name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_teacher_name.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Please enter teacher name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onPermissionResult(int requestCode, boolean isGranted, Object extras) {

    }

    private void save() {
        ttSubject.setAdditionNotes(et_additional_notes.getText().toString());
        ttSubject.setSubjectName(et_subject_name.getText().toString());
        ttSubject.setSubjectTeacher(et_teacher_name.getText().toString().trim());

        SharedPreferences mPrefs = getSharedPreferences("TS", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ttSubject); // myObject - instance of MyObject
        prefsEditor.putString(formKey(), json);
        prefsEditor.commit();
        finish();

        scheduleNotification();


        Toast.makeText(this, "Saved successfully.", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, TimeTableActivity.class);
        startActivity(i);
    }

    private void scheduleNotification() {
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        calSet.set(Calendar.HOUR_OF_DAY, ttSubject.getTime().getStartHour());
        calSet.set(Calendar.MINUTE, ttSubject.getTime().getStartMin());
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        Intent intent1 = new Intent(AddEditTTSubject.this, AlarmReceiver.class);
        intent1.putExtra("Type", AlarmReceiver.TYPE_TIME_TABLE);
        intent1.putExtra("subject", ttSubject.getDay().toString() + "|" + ttSubject.getTime().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddEditTTSubject.this, 0, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) AddEditTTSubject.this.getSystemService(AddEditTTSubject.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, TimeTableActivity.class);
        startActivity(i);
    }

    private String formKey() {
        return ttSubject.getDay().toString() + "|" + ttSubject.getTime().toString();
    }
}

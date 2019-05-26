package com.abhishek.taskscheduler.task;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.abhishek.taskscheduler.R;
import com.abhishek.taskscheduler.models.TaskModel;
import com.abhishek.taskscheduler.receiver.AlarmReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskAddEditActivity extends AppCompatActivity {
    private EditText et_task_name;
    private TextView tv_date;
    private EditText et_additional_notes;
    private Button btn_done;

    private TaskModel taskToOpen;


    Calendar date = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskToOpen = (TaskModel) getIntent().getSerializableExtra("Task");

        setContentView(R.layout.activity_task_add_edit);
        et_task_name = (EditText) findViewById(R.id.et_task_name);
        tv_date = (TextView) findViewById(R.id.tv_date);
        et_additional_notes = (EditText) findViewById(R.id.et_additional_notes);
        btn_done = (Button) findViewById(R.id.btn_done);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker();
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    save();
                }
            }
        });

        //show task
        if (taskToOpen != null) {
            et_task_name.setText(taskToOpen.getTaskName());
            et_additional_notes.setText(taskToOpen.getTaskNotes());
            date.setTimeInMillis(taskToOpen.getTaskDateTimeInMillis());
        }

        showDate();
    }

    private void save() {
        TaskModel taskModel = new TaskModel();
        taskModel.setTaskDateTimeInMillis(date.getTimeInMillis());
        taskModel.setTaskName(et_task_name.getText().toString().trim());
        taskModel.setTaskNotes(et_additional_notes.getText().toString().trim());


        ArrayList<TaskModel> taskModels = getTaskList();
        if (taskToOpen != null) {
            removePreviousFromList(taskModels);
        }
        taskModels.add(taskModel);

        SharedPreferences mPrefs = getSharedPreferences("TS", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskModels); // myObject - instance of MyObject
        prefsEditor.putString("TaskList", json);
        prefsEditor.commit();
        finish();

        scheduleNotification();

        Toast.makeText(this, "Saved successfully.", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, TaskActivity.class);
        startActivity(i);
    }

    private void scheduleNotification() {
        Calendar calSet = (Calendar) date.clone();
        calSet.set(Calendar.HOUR_OF_DAY, date.get(Calendar.HOUR_OF_DAY) - 2);
        calSet.set(Calendar.MINUTE, date.get(Calendar.MINUTE));
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        Intent intent1 = new Intent(TaskAddEditActivity.this, AlarmReceiver.class);
        intent1.putExtra("Type", AlarmReceiver.TYPE_TASK);
        intent1.putExtra("taskName", et_task_name.getText().toString().trim());
        intent1.putExtra("taskTime", getHoursToDisplay());
        intent1.putExtra("taskNotes", et_task_name.getText().toString().trim());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(TaskAddEditActivity.this, 0, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) TaskAddEditActivity.this.getSystemService(TaskAddEditActivity.this.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, TaskActivity.class);
        startActivity(i);
    }

    private void removePreviousFromList(ArrayList<TaskModel> taskModels) {
        for (TaskModel taskModel : taskModels) {
            if (taskModel.getTaskName().equals(taskToOpen.getTaskName())) {
                taskModels.remove(taskModel);
                break;
            }
        }
    }


    public ArrayList<TaskModel> getTaskList() {
        ArrayList<TaskModel> taskModels = new ArrayList<>();
        SharedPreferences mPrefs = getSharedPreferences("TS", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("TaskList", "");
        if (!json.isEmpty()) {
            taskModels = gson.fromJson(json, new TypeToken<List<TaskModel>>() {
            }.getType());
        }
        return taskModels;
    }

    public void showDateTimePicker() {
        new DatePickerDialog(TaskAddEditActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(TaskAddEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        showDate();
                    }
                }, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), false).show();
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE)).show();
    }

    private void showDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Date date = this.date.getTime();
        tv_date.setText(dateFormat.format(date));
    }

    private String getHoursToDisplay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        Date date = this.date.getTime();
        return dateFormat.format(date);
    }

    private boolean validate() {
        if (et_task_name.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Please enter the task name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}

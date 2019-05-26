package com.abhishek.taskscheduler.task;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.abhishek.taskscheduler.AbstractBaseActivity;
import com.abhishek.taskscheduler.R;
import com.abhishek.taskscheduler.adapters.TaskAdapter;
import com.abhishek.taskscheduler.models.TaskModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AbstractBaseActivity {

    private ArrayList<TaskModel> taskModels = new ArrayList<>();
    private TaskAdapter taskAdapter;
    private ListView lv_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        lv_task = (ListView) findViewById(R.id.lv_task);

        taskModels = getTaskList();
        taskAdapter = new TaskAdapter(this, taskModels);
        lv_task.setAdapter(taskAdapter);
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

    @Override
    public void onPermissionResult(int requestCode, boolean isGranted, Object extras) {

    }
}

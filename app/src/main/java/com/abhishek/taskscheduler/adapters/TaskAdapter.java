package com.abhishek.taskscheduler.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.taskscheduler.R;
import com.abhishek.taskscheduler.models.TaskModel;
import com.abhishek.taskscheduler.task.TaskAddEditActivity;

import java.util.ArrayList;


public class TaskAdapter extends BaseAdapter {
    private ArrayList<TaskModel> taskList;
    private Context context;

    public TaskAdapter(Context context, ArrayList<TaskModel> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return taskList.size() + 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.task_item, null);
        }

        ImageView iv_taskimage = view.findViewById(R.id.iv_taskimage);
        TextView tv_taskname = view.findViewById(R.id.tv_taskname);

        if (i == 0) {
            iv_taskimage.setVisibility(View.VISIBLE);
            tv_taskname.setText("Add New Task");
        } else {
            iv_taskimage.setVisibility(View.INVISIBLE);
            tv_taskname.setText(taskList.get(i - 1).getTaskName());
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
                Intent intent = new Intent(context, TaskAddEditActivity.class);
                if (i != 0) {
                    intent.putExtra("Task",taskList.get(i - 1));
                }
                context.startActivity(intent);
            }
        });

        return view;
    }
}

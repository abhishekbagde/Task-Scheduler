package com.abhishek.taskscheduler.models;

import java.io.Serializable;

/**
 * Created by siddharthyadav on 08/04/18.
 */

public class TaskModel implements Serializable {
    private long taskDateTimeInMillis;
    private String taskName = "";
    private String taskNotes = "";

    public long getTaskDateTimeInMillis() {
        return taskDateTimeInMillis;
    }

    public void setTaskDateTimeInMillis(long taskDateTimeInMillis) {
        this.taskDateTimeInMillis = taskDateTimeInMillis;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskNotes() {
        return taskNotes;
    }

    public void setTaskNotes(String taskNotes) {
        this.taskNotes = taskNotes;
    }
}

package com.abhishek.taskscheduler.models;


import com.abhishek.taskscheduler.enums.TTSubjectTimeSlots;
import com.abhishek.taskscheduler.enums.TTSujectDays;

import java.io.Serializable;

public class TTSubject implements Serializable {
    private TTSujectDays day;
    private TTSubjectTimeSlots time;
    private String subjectName = "";
    private String subjectTeacher = "";
    private String additionNotes = "";

    public TTSujectDays getDay() {
        return day;
    }

    public void setDay(TTSujectDays day) {
        this.day = day;
    }

    public TTSubjectTimeSlots getTime() {
        return time;
    }

    public void setTime(TTSubjectTimeSlots time) {
        this.time = time;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectTeacher() {
        return subjectTeacher;
    }

    public void setSubjectTeacher(String subjectTeacher) {
        this.subjectTeacher = subjectTeacher;
    }

    public String getAdditionNotes() {
        return additionNotes;
    }

    public void setAdditionNotes(String additionNotes) {
        this.additionNotes = additionNotes;
    }
}

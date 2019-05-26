package com.abhishek.taskscheduler.timetable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.abhishek.taskscheduler.R;
import com.abhishek.taskscheduler.enums.TTSubjectTimeSlots;
import com.abhishek.taskscheduler.enums.TTSujectDays;
import com.abhishek.taskscheduler.models.TTSubject;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TimeTableActivity extends AppCompatActivity {
    private LinearLayout ll_subject_container;
    List<TTSubject> subjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subjects = getSubjects();
        setContentView(R.layout.activity_time_table);
        ll_subject_container = (LinearLayout) findViewById(R.id.ll_subject_container);
        addAllSubjects();
    }


    private void addAllSubjects() {
        for (TTSujectDays day : TTSujectDays.values()) {
            LinearLayout child = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tt_subject_layout, null);
            Button btn_day = child.findViewById(R.id.btn_day);
            Button btn_subject1 = child.findViewById(R.id.btn_subject1);
            Button btn_subject2 = child.findViewById(R.id.btn_subject2);
            Button btn_subject3 = child.findViewById(R.id.btn_subject3);
            Button btn_subject4 = child.findViewById(R.id.btn_subject4);
            Button btn_subject5 = child.findViewById(R.id.btn_subject5);
            Button btn_subject6 = child.findViewById(R.id.btn_subject6);
            btn_day.setText(day.getShortName());

            btn_subject1.setTag(R.id.btn_day, day);
            btn_subject1.setTag(R.id.btn_done, TTSubjectTimeSlots.TEN_2_ELEVEN);
            btn_subject1.setText(getSubjectByDayAndTimeSlot(btn_subject1, day, TTSubjectTimeSlots.TEN_2_ELEVEN));

            btn_subject2.setTag(R.id.btn_day, day);
            btn_subject2.setTag(R.id.btn_done, TTSubjectTimeSlots.ELEVEN_2_TWELVE);
            btn_subject2.setText(getSubjectByDayAndTimeSlot(btn_subject1, day, TTSubjectTimeSlots.ELEVEN_2_TWELVE));

            btn_subject3.setTag(R.id.btn_day, day);
            btn_subject3.setTag(R.id.btn_done, TTSubjectTimeSlots.TWELVE_2_ONE);
            btn_subject3.setText(getSubjectByDayAndTimeSlot(btn_subject1, day, TTSubjectTimeSlots.TWELVE_2_ONE));

            btn_subject4.setTag(R.id.btn_day, day);
            btn_subject4.setTag(R.id.btn_done, TTSubjectTimeSlots.TWO_2_THREE);
            btn_subject4.setText(getSubjectByDayAndTimeSlot(btn_subject1, day, TTSubjectTimeSlots.TWO_2_THREE));

            btn_subject5.setTag(R.id.btn_day, day);
            btn_subject5.setTag(R.id.btn_done, TTSubjectTimeSlots.THREE_2_FOUR);
            btn_subject5.setText(getSubjectByDayAndTimeSlot(btn_subject1, day, TTSubjectTimeSlots.THREE_2_FOUR));

            btn_subject6.setTag(R.id.btn_day, day);
            btn_subject6.setTag(R.id.btn_done, TTSubjectTimeSlots.FOUR_2_FIVE);
            btn_subject6.setText(getSubjectByDayAndTimeSlot(btn_subject1, day, TTSubjectTimeSlots.FOUR_2_FIVE));

            ll_subject_container.addView(child);
        }
    }

    private String getSubjectByDayAndTimeSlot(Button btn, TTSujectDays day, TTSubjectTimeSlots timeSlot) {
        for (TTSubject subject : subjects) {
            if (subject.getDay() == day && subject.getTime() == timeSlot) {
                return subject.getSubjectName();
            }
        }
        return "";
    }

    private TTSubject getSubjectByDayAndTimeSlot(TTSujectDays day, TTSubjectTimeSlots timeSlot) {
        for (TTSubject subject : subjects) {
            if (subject.getDay() == day && subject.getTime() == timeSlot) {
                return subject;
            }
        }
        return null;
    }

    public void onSubjectClick(View view) {
        TTSubject ttSubject = getSubjectByDayAndTimeSlot((TTSujectDays) view.getTag(R.id.btn_day),
                (TTSubjectTimeSlots) view.getTag(R.id.btn_done));
        if (ttSubject == null) {
            ttSubject = new TTSubject();
            ttSubject.setSubjectName(((Button) view).getText().toString());
            ttSubject.setAdditionNotes("");
            ttSubject.setSubjectTeacher("");
            ttSubject.setDay((TTSujectDays) view.getTag(R.id.btn_day));
            ttSubject.setTime((TTSubjectTimeSlots) view.getTag(R.id.btn_done));
        }
        finish();
        Intent i = new Intent(this, AddEditTTSubject.class);
        i.putExtra("subject", ttSubject);
        startActivity(i);
    }

    public List<TTSubject> getSubjects() {
        subjects.clear();
        SharedPreferences mPrefs = getSharedPreferences("TS", MODE_PRIVATE);
        Gson gson = new Gson();
        for (TTSujectDays day : TTSujectDays.values()) {
            for (TTSubjectTimeSlots timeSlot : TTSubjectTimeSlots.values()) {
                String json = mPrefs.getString(day.toString() + "|" + timeSlot.toString(), "");
                if (!json.isEmpty()) {
                    TTSubject subject = gson.fromJson(json, TTSubject.class);
                    subjects.add(subject);
                }
            }
        }
        return subjects;
    }
}

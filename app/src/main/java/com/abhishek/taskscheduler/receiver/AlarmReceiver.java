package com.abhishek.taskscheduler.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.abhishek.taskscheduler.HomeScreen;
import com.abhishek.taskscheduler.R;
import com.abhishek.taskscheduler.models.TTSubject;
import com.google.gson.Gson;


public class AlarmReceiver extends BroadcastReceiver {
    public final static int TYPE_TIME_TABLE = 0;
    public final static int TYPE_TASK = 1;
    public final static int TYPE_PAYMENT = 2;
    static int MID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getIntExtra("Type", -1)) {
            case TYPE_TIME_TABLE:
                scheduleTimeTableAlarm(context, intent);
                break;
            case TYPE_TASK:
                scheduleTaskAlarm(context, intent);
                break;
            case TYPE_PAYMENT:
                schedulePaymentAlram(context, intent);
                break;

        }
    }

    private void schedulePaymentAlram(Context context, Intent intent) {
        String taskName = intent.getStringExtra("taskName");
        String taskTime = intent.getStringExtra("taskTime");
        String taskNotes = intent.getStringExtra("taskNotes");
        showNotification(context, "You have a payment to do i.e. " + taskName + " at " + taskTime, taskNotes);
    }

    private void scheduleTaskAlarm(Context context, Intent intent) {
        String taskName = intent.getStringExtra("taskName");
        String taskTime = intent.getStringExtra("taskTime");
        String taskNotes = intent.getStringExtra("taskNotes");
        showNotification(context, "You have a task to do i.e. " + taskName + " at " + taskTime, taskNotes);
    }

    private void scheduleTimeTableAlarm(Context context, Intent intent) {
        String subjectKey = intent.getStringExtra("subject");
        TTSubject ttSubject = getSubjects(context, subjectKey);
        showNotification(context, "Its time for " + ttSubject.getSubjectName() + " Lecture.",
                ttSubject.getSubjectTeacher() + " sir/mam going to take this. Be ready !!");
    }


    private void showNotification(Context context, String title, String body) {
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, HomeScreen.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(alarmSound)
                .setAutoCancel(false).setWhen(when)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationManager.notify(MID, mNotifyBuilder.build());
        MID++;
    }


    public TTSubject getSubjects(Context context, String key) {
        SharedPreferences mPrefs = context.getSharedPreferences("TS", context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(key, "");
        if (!json.isEmpty()) {
            return gson.fromJson(json, TTSubject.class);
        }
        return null;
    }
}

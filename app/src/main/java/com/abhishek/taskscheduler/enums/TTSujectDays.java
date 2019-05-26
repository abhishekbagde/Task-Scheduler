package com.abhishek.taskscheduler.enums;

/**
 * Created by siddharthyadav on 08/04/18.
 */

public enum TTSujectDays {
    MON("Monday", "MON"),
    TUE("Tuesday", "TUE"),
    WED("Wednesday", "WED"),
    THU("Thursday", "THU"),
    FRI("Friday", "FRI"),
    SAT("Saturday", "SAT");

    String mName;
    String mShortName;

    TTSujectDays(String dayName, String shortName) {
        mName = dayName;
        mShortName = shortName;
    }

    public String getName() {
        return mName;
    }

    public String getShortName() {
        return mShortName;
    }
}

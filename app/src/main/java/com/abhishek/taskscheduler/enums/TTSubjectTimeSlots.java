package com.abhishek.taskscheduler.enums;


public enum TTSubjectTimeSlots {
    TEN_2_ELEVEN("10am to 11am", 9, 55),
    ELEVEN_2_TWELVE("11am to 12pm", 10, 55),
    TWELVE_2_ONE("12pm to 1pm", 11, 55),
    TWO_2_THREE("2pm to 3pm", 13, 55),
    THREE_2_FOUR("3pm to 4pm", 14, 55),
    FOUR_2_FIVE("4pm to 5pm", 15, 55);

    String mName;
    int mStartHour;
    int mStartMin;

    TTSubjectTimeSlots(String slotName, int startHour, int startMin) {
        mName = slotName;
        mStartHour = startHour;
        mStartMin = startMin;
    }

    public String getName() {
        return mName;
    }

    public int getStartHour() {
        return mStartHour;
    }

    public int getStartMin() {
        return mStartMin;
    }
}

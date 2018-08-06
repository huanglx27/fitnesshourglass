package com.fitnesshourglass.android.Timing;

public class TimeSetted {

    public static final long SECOND_TO_MILL = 1000;

    private int minute;//定时的分钟位

    private  int second;//定时的秒钟位

    private int groupCouted;//已进行的组数

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getGroupCouted() {
        return groupCouted;
    }

    public void setGroupCouted(int groupCouted) {
        this.groupCouted = groupCouted;
    }

    public long getTotalMill(){
        return (getMinute() * 60 + getSecond()) * SECOND_TO_MILL;
    }

    public void addGroup(){
        groupCouted ++;
    }

}

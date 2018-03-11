package com.springapp.mvc.model;

public class ExamTime {
    private long minutes;
    private long seconds;

    public ExamTime(long minutes, long seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }
}

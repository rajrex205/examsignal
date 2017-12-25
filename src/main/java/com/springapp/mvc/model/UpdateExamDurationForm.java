package com.springapp.mvc.model;

public class UpdateExamDurationForm {
    private long examId;
    private String duration;

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

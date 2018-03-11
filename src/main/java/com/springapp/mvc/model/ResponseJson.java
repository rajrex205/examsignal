package com.springapp.mvc.model;

public class ResponseJson {
    String message;
    String subjectId;

    public ResponseJson() {
    }

    public ResponseJson(String message, String subjectId) {
        this.message = message;
        this.subjectId = subjectId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message= message;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
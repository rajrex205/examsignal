package com.springapp.mvc.model;

public class GetQuestion {
    private String subjectId;
    private String questionId;

    public GetQuestion() {
    }

    public GetQuestion(String subjectId, String questionId) {
        this.subjectId = subjectId;
        this.questionId = questionId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}

package com.springapp.mvc.model;

public class QuestionForm {
    private String response;
    private String questionId;
    private String subjectId;
    private String selectedOption;

    public QuestionForm() {
    }

    public QuestionForm(String response, String questionId, String subjectId, String selectedOption) {
        this.response = response;
        this.questionId = questionId;
        this.selectedOption = selectedOption;
        this.subjectId = subjectId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}

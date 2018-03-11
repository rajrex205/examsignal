package com.springapp.mvc.model;

public class ExamSession {
    private long subjectId;
    private String subjectName;
    private int numberOfQuestions;
    private String[] questionAttemptStatus;
    private int[] optionSelected;
    private int lastQuestionAttempted;

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public String[] getQuestionAttemptStatus() {
        return questionAttemptStatus;
    }

    public void setQuestionAttemptStatus(String[] questionAttemptStatus) {
        this.questionAttemptStatus = questionAttemptStatus;
    }

    public int[] getOptionSelected() {
        return optionSelected;
    }

    public void setOptionSelected(int[] optionSelected) {
        this.optionSelected = optionSelected;
    }

    public int getLastQuestionAttempted() {
        return lastQuestionAttempted;
    }

    public void setLastQuestionAttempted(int lastQuestionAttempted) {
        this.lastQuestionAttempted = lastQuestionAttempted;
    }
}

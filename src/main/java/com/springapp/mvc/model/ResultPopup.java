package com.springapp.mvc.model;

public class ResultPopup {

    private Long scoreId;
    private String marksSecured;
    private String totalMarks;
    private String percentage;
    private String percentageDescription;
    private String image;
    private String timeTaken;
    private String totalTime;

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public String getMarksSecured() {
        return marksSecured;
    }

    public void setMarksSecured(String marksSecured) {
        this.marksSecured = marksSecured;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getPercentageDescription() {
        return percentageDescription;
    }

    public void setPercentageDescription(String percentageDescription) {
        this.percentageDescription = percentageDescription;
    }
}

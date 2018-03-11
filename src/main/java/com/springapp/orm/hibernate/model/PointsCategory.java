package com.springapp.orm.hibernate.model;

public enum PointsCategory {
    EXAM("Exam",0),REFERRAL("Referral",5),SOCIAL_FB("SocialFB",5);

    private int pointValue;
    private String reason;
    PointsCategory(String reason,int pointValue){
        this.reason = reason;
        this.pointValue = pointValue;
    }

    public int getPoints(){
        return this.pointValue;
    }

    public String getReason() {
        return this.reason;
    }
}

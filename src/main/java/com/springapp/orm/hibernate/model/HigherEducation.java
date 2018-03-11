package com.springapp.orm.hibernate.model;

public class HigherEducation {
    private long id;
    private String education;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}

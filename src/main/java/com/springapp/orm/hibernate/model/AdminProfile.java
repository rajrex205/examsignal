package com.springapp.orm.hibernate.model;

import java.sql.Date;

public class AdminProfile {
    private long id;
    private String uniqueId;
    private String description;
    private String courseRole;
    private String subjectRole;
    private int isDeleted;
    private Date createTS;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseRole() {
        return courseRole;
    }

    public void setCourseRole(String courseRole) {
        this.courseRole = courseRole;
    }

    public String getSubjectRole() {
        return subjectRole;
    }

    public void setSubjectRole(String subjectRole) {
        this.subjectRole = subjectRole;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTS() {
        return createTS;
    }

    public void setCreateTS(Date createTS) {
        this.createTS = createTS;
    }
}

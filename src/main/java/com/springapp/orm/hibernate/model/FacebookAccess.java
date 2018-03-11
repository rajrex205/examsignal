package com.springapp.orm.hibernate.model;

import java.util.Date;

public class FacebookAccess {
    private long id;
    private String fbId;
    private String email;
    private int isDeleted;
    private Date createdTS;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedTS() {
        return createdTS;
    }

    public void setCreatedTS(Date createdTS) {
        this.createdTS = createdTS;
    }
}

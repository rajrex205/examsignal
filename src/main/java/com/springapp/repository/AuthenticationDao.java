package com.springapp.repository;

import com.springapp.orm.hibernate.model.AuthAccess;

public interface AuthenticationDao {
    public AuthAccess getUser(String emailId, String authMode);
    public void resetPassword(String password, String emailId);
}

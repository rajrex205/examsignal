package com.springapp.service;

import com.springapp.orm.hibernate.model.AdminProfile;
import com.springapp.orm.hibernate.model.UserProfile;
import com.springapp.repository.AdminProfileDaoImpl;
import com.springapp.repository.UserProfileDaoImpl;

public class LoginHelper {
    private UserProfileDaoImpl userProfileDao;
    private AdminProfileDaoImpl adminProfileDao;

    public void setUserProfileDao(UserProfileDaoImpl userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    public void setAdminProfileDao(AdminProfileDaoImpl adminProfileDao) {
        this.adminProfileDao = adminProfileDao;
    }

    public UserProfile getUserProfile(long userId){
        return userProfileDao.fetchProfileFromID(userId);
    }

    public AdminProfile getAdminProfile(long adminId){
        return adminProfileDao.fetchProfileFromID(adminId);
    }
}

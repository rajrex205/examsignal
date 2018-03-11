package com.springapp.service;

import com.springapp.orm.hibernate.model.*;
import com.springapp.repository.AuthAccessDaoImpl;
import com.springapp.repository.FacebookAccessDaoImpl;
import com.springapp.repository.PointsDaoImpl;
import com.springapp.repository.UserProfileDaoImpl;
import com.springapp.request.RegistrationRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

public class RegistrationService {
    private AuthAccessDaoImpl authAccessDaoImpl;
    private UserProfileDaoImpl userProfileDaoImpl;
    private PasswordEncryptionService passwordEncryptionService;
    private UniqueUserIdGeneratorService uniqueUserIdGeneratorService;
    private PointsDaoImpl pointsDao;
    private FacebookAccessDaoImpl facebookAccessDao;
    private static final PointsCategory REFERRAL_POINTS_CATEGORY = PointsCategory.REFERRAL;

    public void setFacebookAccessDao(FacebookAccessDaoImpl facebookAccessDao) {
        this.facebookAccessDao = facebookAccessDao;
    }

    public void setPointsDao(PointsDaoImpl pointsDao) {
        this.pointsDao = pointsDao;
    }

    public void setAuthAccessDaoImpl(AuthAccessDaoImpl authAccessDaoImpl) {
        this.authAccessDaoImpl = authAccessDaoImpl;
    }

    public void setUserProfileDaoImpl(UserProfileDaoImpl userProfileDaoImpl) {
        this.userProfileDaoImpl = userProfileDaoImpl;
    }

    public void setPasswordEncryptionService(PasswordEncryptionService passwordEncryptionService) {
        this.passwordEncryptionService = passwordEncryptionService;
    }

    public void setUniqueUserIdGeneratorService(UniqueUserIdGeneratorService uniqueUserIdGeneratorService) {
        this.uniqueUserIdGeneratorService = uniqueUserIdGeneratorService;
    }

    public long register(RegistrationRequest registrationRequest, String type){
        AuthAccess existingUser = authAccessDaoImpl.getUser(registrationRequest.getEmail(),registrationRequest.getAuthMode());
        if(null == existingUser){
            String encryptedPassword = passwordEncryptionService.getEncryptedPassword(registrationRequest.getPassword());
            registrationRequest.setEncryptedPassword(encryptedPassword);
            authAccessDaoImpl.insertAuthAccess(registrationRequest);
            AuthAccess insertedUser = authAccessDaoImpl.getUser(registrationRequest.getEmail(),registrationRequest.getAuthMode());
            if(null != insertedUser){
                registrationRequest.setId(insertedUser.getId());
                registrationRequest.setUserID(uniqueUserIdGeneratorService.getUniqueCode(type, registrationRequest.getFirstName(),
                        registrationRequest.getLastName(),registrationRequest.getId()));
                userProfileDaoImpl.insertUsrProfile(registrationRequest);
                if(StringUtils.isNotBlank(registrationRequest.getReferralCode())){
                    Long userId = userProfileDaoImpl.fetchIdFromUniqueId(registrationRequest.getReferralCode());
                    if(null != userId){
                        Points p = new Points();
                        p.setUserId(userId);
                        p.setReasonCode(REFERRAL_POINTS_CATEGORY.getReason());
                        p.setPoints(REFERRAL_POINTS_CATEGORY.getPoints());
                        p.setReasonDescription("" + registrationRequest.getId());
                        pointsDao.insertPoints(p);
                    }
                }
            }
            return insertedUser.getId();
        } else{
            return -1;
        }
    }
    public boolean registerForSocialSignUp(String emailId, String authMode){
        AuthAccess existingUser = authAccessDaoImpl.getUser(emailId, authMode);
        if(null == existingUser){
            RegistrationRequest r = new RegistrationRequest();
            r.setEmail(emailId);
            r.setEncryptedPassword("");
            r.setAuthMode(authMode);
            authAccessDaoImpl.insertAuthAccess(r);

            AuthAccess insertedUser = authAccessDaoImpl.getUser(emailId,authMode);
            String forUniqueId = StringUtils.substringBeforeLast(emailId,"@");
            if(null != insertedUser){
                userProfileDaoImpl.createBlankProfileForSocialSignUp(insertedUser.getId(),
                        uniqueUserIdGeneratorService.getUniqueCode("S", forUniqueId.substring(0,1),
                                forUniqueId.substring(forUniqueId.length()-1,forUniqueId.length()) , insertedUser.getId()));
            }
            return true;
        } else{
            return false;
        }
    }

    public boolean registerFacebook(String fbId, String emailId){
        facebookAccessDao.insert(fbId,emailId);
        return true;
    }

    public UserProfile getUserProfile(long userId){
        return userProfileDaoImpl.fetchProfileFromID(userId);
    }

}
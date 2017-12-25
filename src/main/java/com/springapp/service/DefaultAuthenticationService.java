package com.springapp.service;

import com.springapp.mvc.model.ViewPreferredCourse;
import com.springapp.mvc.model.ViewTestimonial;
import com.springapp.orm.hibernate.model.AuthAccess;
import com.springapp.orm.hibernate.model.FacebookAccess;
import com.springapp.orm.hibernate.model.HigherEducation;
import com.springapp.orm.hibernate.model.ResetCode;
import com.springapp.repository.*;
import com.springapp.request.AuthenticationRequest;

import java.util.List;

public class DefaultAuthenticationService implements AuthenticationService {
    private AuthAccessDaoImpl authenticationDao;
    private PasswordEncryptionService passwordEncryptionService;
    private HigherEducationDaoImpl higherEducationDao;
    private TestimonialDaoImpl testimonialDao;
    private CourseDaoImpl courseDao;
    private FacebookAccessDaoImpl facebookAccessDao;
    private ResetCodeDaoImpl resetCodeDao;

    public void setResetCodeDao(ResetCodeDaoImpl resetCodeDao) {
        this.resetCodeDao = resetCodeDao;
    }

    public void setFacebookAccessDao(FacebookAccessDaoImpl facebookAccessDao) {
        this.facebookAccessDao = facebookAccessDao;
    }

    public void setCourseDao(CourseDaoImpl courseDao) {
        this.courseDao = courseDao;
    }

    public void setTestimonialDao(TestimonialDaoImpl testimonialDao) {
        this.testimonialDao = testimonialDao;
    }

    public void setAuthenticationDao(AuthAccessDaoImpl authenticationDao) {

        this.authenticationDao = authenticationDao;
    }

    public void setPasswordEncryptionService(PasswordEncryptionService passwordEncryptionService) {
        this.passwordEncryptionService = passwordEncryptionService;
    }

    public void setHigherEducationDao(HigherEducationDaoImpl higherEducationDao) {
        this.higherEducationDao = higherEducationDao;
    }

    public AuthAccess isAuthorized(AuthenticationRequest authenticationRequest){
        if(null != authenticationRequest && authenticationRequest.isValidRequest()){
            AuthAccess authAccess = authenticationDao.getUser(authenticationRequest.getEmailId(),authenticationRequest.getAuthMode());
            if(null != authAccess){
                boolean isPasswordMatching = passwordEncryptionService.isPasswordMatching(authenticationRequest.getPassword(),authAccess.getPassword());
                return isPasswordMatching && !isDeleted(authAccess) ? authAccess : null;
            }else {
                return null;
            }
        }
        return null;
    }

    public AuthAccess getAuthAccessForSocialSignIn(String emailId, String authMode){
        return authenticationDao.getUser(emailId, authMode);
    }

    public List<HigherEducation> fetchHigherEducation(){
        return higherEducationDao.fetchHigherEducation();
    }

    public  List<ViewTestimonial> fetchTestimonials(){
        return testimonialDao.fetchTestimonials();
    }

    public List<ViewPreferredCourse> getPublicGroupCourses(){
        return courseDao.fetchPublicGroupCourses();
    }

    public FacebookAccess fetchFacebookAccessOnFBId(String fbId){
        return facebookAccessDao.fetchOnFbId(fbId);
    }

    public boolean updateFacebookAccessForEmailId(String oldTempEmailId, String newEmailId){
        facebookAccessDao.updateFacebookAccessForEmailId(oldTempEmailId,newEmailId);
        return true;
    }

    public boolean updateAuthAccessForEmailId(String oldTempEmailId, String newEmailId){
        authenticationDao.updateAuthAccessForEmailId(oldTempEmailId,newEmailId);
        return true;
    }

    public boolean resetPassword(String newPassword, String emailId){
        authenticationDao.resetPassword(newPassword, emailId);
        return true;
    }

    public boolean setCode(ResetCode resetCode){
        resetCodeDao.setCode(resetCode);
        return true;
    }

    public boolean verifyCode(ResetCode resetCode){
        return resetCodeDao.codeMatches(resetCode);
    }

    public boolean isRegisteredUser(AuthenticationRequest authenticationRequest){
        if(null != authenticationRequest){
            AuthAccess authAccess = authenticationDao.getUser(authenticationRequest.getEmailId(),authenticationRequest.getAuthMode());
            return null != authAccess;
        }
        return false;
    }

    private boolean isDeleted(AuthAccess authAccess){
        return authAccess.getIsDeleted()==1;
    }

}

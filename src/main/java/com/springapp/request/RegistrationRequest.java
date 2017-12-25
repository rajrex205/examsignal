package com.springapp.request;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class RegistrationRequest implements ActionRequest{
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phone;
    private String address;
    private String highestEducation;
    private String referralCode;
    private List<Long> preferredCourse;
    private String password;
    private String confirmPassword;
    private String encryptedPassword;
    private String authMode;
    private long id;
    private String userID;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public List<Long> getPreferredCourse() {
        return preferredCourse;
    }

    public void setPreferredCourse(List<Long> preferredCourse) {
        this.preferredCourse = preferredCourse;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getAuthMode() {
        return authMode;
    }

    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isValidRequest(){
        boolean mandatoryField = StringUtils.isNotBlank(getEmail()) && StringUtils.isNoneBlank(getPassword()) &&
                StringUtils.isNotBlank(getConfirmPassword()) && StringUtils.equals(getPassword(),getConfirmPassword()) &&
                StringUtils.isNotBlank(getFirstName()) && StringUtils.isNotBlank(getLastName()) && StringUtils.isNotBlank(getPhone())
                && StringUtils.isNotBlank(getHighestEducation()) && (null != getPreferredCourse() && getPreferredCourse().size()>0);
        if(mandatoryField){
            boolean lengthCheck = getEmail().length()<=100 && getPassword().length()<=50 && getFirstName().length()<=50 &&
                    getLastName().length()<=50 && getPhone().length()<=20 && getHighestEducation().length()<=50
                    && (getGender().length()==4 || getGender().length()==6);
            return lengthCheck;
        }
        return false;
    }
}

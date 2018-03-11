package com.springapp.mvc.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EditUserProfileForm {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private String highestEducation;
    private String address;
    private String preferredCourses;
    private List<Long> preferredCoursesList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPreferredCourses() {
        return preferredCourses;
    }

    public void setPreferredCourses(String preferredCourses) {
        this.preferredCourses = preferredCourses;
    }

    public List<Long> getPreferredCoursesList() {
        return preferredCoursesList;
    }

    public void setPreferredCoursesList(List<Long> preferredCoursesList) {
        this.preferredCoursesList = preferredCoursesList;
    }

    public boolean isValidRequest(){
        boolean mandatoryField = StringUtils.isNotBlank(getEmail()) &&
                StringUtils.isNotBlank(getFirstName()) && StringUtils.isNotBlank(getLastName()) && StringUtils.isNotBlank(getPhone())
                && StringUtils.isNotBlank(getHighestEducation()) && StringUtils.isNotBlank(getPreferredCourses());
        if(mandatoryField){
            boolean lengthCheck = getEmail().length()<=100 && getFirstName().length()<=50 &&
                    getLastName().length()<=50 && getPhone().length()<=20 && getHighestEducation().length()<=50
                    && (getGender().length()==4 || getGender().length()==6);
            return lengthCheck;
        }
        return false;
    }

    public void setPreferredCoursesList(){
        String preferredCourses = this.getPreferredCourses();
        if(StringUtils.isNotBlank(preferredCourses)){
            String[] preferredCoursesArray = preferredCourses.split(",");
            List<Long> formattedPreferredCourse = null;
            if(null != preferredCoursesArray && preferredCoursesArray.length>0){
                formattedPreferredCourse = new ArrayList<Long>();
                for(String c: preferredCoursesArray){
                    formattedPreferredCourse.add(Long.parseLong(c));
                }
            }
            this.setPreferredCoursesList(formattedPreferredCourse);
        }
    }
}
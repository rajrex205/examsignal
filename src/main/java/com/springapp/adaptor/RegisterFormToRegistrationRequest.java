package com.springapp.adaptor;

import com.springapp.mvc.model.RegisterForm;
import com.springapp.orm.hibernate.model.AuthMode;
import com.springapp.request.RegistrationRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RegisterFormToRegistrationRequest {

    public RegistrationRequest getRegistrationRequest(RegisterForm registerForm){
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setEmail(registerForm.getEmail());
        registrationRequest.setPassword(registerForm.getPassword());
        registrationRequest.setConfirmPassword(registerForm.getConfirmPassword());
        registrationRequest.setFirstName(registerForm.getFirstName());
        registrationRequest.setLastName(registerForm.getLastName());
        registrationRequest.setGender(registerForm.getGender());
        registrationRequest.setPhone(registerForm.getPhone());
        registrationRequest.setAddress(registerForm.getAddress());
        registrationRequest.setHighestEducation(registerForm.getHighestEducation());
        String preferredCourses = registerForm.getPreferredCourse();
        if(StringUtils.isNotBlank(preferredCourses)){
            String[] preferredCoursesArray = preferredCourses.split(",");
            List<Long> formattedPreferredCourse = null;
            if(null != preferredCoursesArray && preferredCoursesArray.length>0){
                formattedPreferredCourse = new ArrayList<Long>();
                for(String c: preferredCoursesArray){
                    formattedPreferredCourse.add(Long.parseLong(c));
                }
            }
            registrationRequest.setPreferredCourse(formattedPreferredCourse);
        }
        registrationRequest.setReferralCode(registerForm.getReferralCode());
        registrationRequest.setAuthMode(AuthMode.DEFAULT.toString());
        return  registrationRequest;
    }
}

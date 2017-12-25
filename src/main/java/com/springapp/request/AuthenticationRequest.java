package com.springapp.request;

import com.springapp.orm.hibernate.model.AuthMode;
import org.apache.commons.lang3.StringUtils;

public class AuthenticationRequest implements ActionRequest{
    private String emailId;
    private String password;
    private String authMode;

    private static final int EMAIL_ID_MAX_LENGTH = 100;
    private static final int PWD_MAX_LENGTH = 50;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = StringUtils.trim(emailId);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = StringUtils.trim(password);
    }

    public String getAuthMode() {
        return authMode;
    }

    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    @Override
    public boolean isValidRequest(){
        if(this.authMode!=null){
            boolean isValidEmail = StringUtils.isNotBlank(this.emailId) && this.emailId.length()<=EMAIL_ID_MAX_LENGTH;
            if(StringUtils.equalsIgnoreCase(AuthMode.DEFAULT.toString(), this.authMode)){
                return isValidEmail && StringUtils.isNotBlank(this.password) && this.password.length()<=PWD_MAX_LENGTH;
            } else{
                return isValidEmail;
            }
        }
        return false;
    }
}

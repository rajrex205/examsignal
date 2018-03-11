package com.springapp.adaptor;

import com.springapp.mvc.model.LoginForm;
import com.springapp.orm.hibernate.model.AuthMode;
import com.springapp.request.AuthenticationRequest;

public class LoginFormToAuthenticationRequest {

    public AuthenticationRequest getAuthenticationRequest(LoginForm loginForm){
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmailId(loginForm.getEmailId());
        authenticationRequest.setPassword(loginForm.getPassword());
        authenticationRequest.setAuthMode(AuthMode.DEFAULT.toString());

        return  authenticationRequest;
    }
}

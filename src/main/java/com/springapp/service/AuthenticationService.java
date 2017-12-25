package com.springapp.service;

import com.springapp.orm.hibernate.model.AuthAccess;
import com.springapp.request.AuthenticationRequest;

public interface AuthenticationService {
    public AuthAccess isAuthorized(AuthenticationRequest authenticationRequest);
}

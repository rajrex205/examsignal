package com.springapp.mvc.controller;

import com.springapp.adaptor.RegisterFormToRegistrationRequest;
import com.springapp.mvc.model.RegisterForm;
import com.springapp.orm.hibernate.model.LoginSessionEnum;
import com.springapp.orm.hibernate.model.UserProfile;
import com.springapp.orm.hibernate.model.UserProfileEnum;
import com.springapp.request.RegistrationRequest;
import com.springapp.service.Constants;
import com.springapp.service.RegistrationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {

    @Autowired
    private RegisterFormToRegistrationRequest registerFormToRegistrationRequest;
    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/registrationForm", method = RequestMethod.GET)
    public ModelAndView registrationPage() {
        return new ModelAndView("registerform","command",new RegisterForm());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView authenticate(HttpServletRequest request,RegisterForm f) {
        RegistrationRequest registrationRequest = registerFormToRegistrationRequest.getRegistrationRequest(f);
        if(null != registrationRequest && registrationRequest.isValidRequest()){
            long id = registrationService.register(registrationRequest,"S");
            if(id != -1){
                UserProfile p = registrationService.getUserProfile(id);
                request.getSession().setAttribute(UserProfileEnum.FIRST_NAME.toString(),registrationRequest.getFirstName());
                request.getSession().setAttribute(UserProfileEnum.LAST_NAME.toString(),registrationRequest.getLastName());
                request.getSession().setAttribute(UserProfileEnum.USER_ID.toString(),p.getUserId());
                request.getSession().setAttribute(UserProfileEnum.SIGN_OUT.toString(), Constants.NORMAL_USER_SIGN_OUT_LINK);
                request.getSession().setAttribute(LoginSessionEnum.ID.toString(),id);
                request.getSession().setAttribute(LoginSessionEnum.EMAIL.toString(),registrationRequest.getEmail());
                request.getSession().setAttribute(LoginSessionEnum.AUTH_MODE.toString(),registrationRequest.getAuthMode());
                request.getSession().setAttribute(LoginSessionEnum.LOGGED_IN.toString(),true);
                request.getSession().setAttribute(LoginSessionEnum.TYPE_OF_USER.toString(),"user");
                return new ModelAndView("redirect:/userProfile");
            } else {
                return new ModelAndView("redirect:/?error=EmailId_Already_Registered");
            }
        } //else
        return new ModelAndView("redirect:/?error=Invalid_Request");
    }
}
package com.springapp.mvc.controller;

import com.springapp.mvc.model.LoginForm;
import com.springapp.mvc.model.RegisterForm;
import com.springapp.mvc.model.ViewPreferredCourse;
import com.springapp.mvc.model.ViewTestimonial;
import com.springapp.orm.hibernate.model.AuthMode;
import com.springapp.orm.hibernate.model.HigherEducation;
import com.springapp.orm.hibernate.model.ResetCode;
import com.springapp.orm.hibernate.model.UserProfileEnum;
import com.springapp.request.AuthenticationRequest;
import com.springapp.service.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DefaultController {

    @Autowired
    private DefaultAuthenticationService defaultAuthenticationService;

    @Autowired
    private TwoWayEncryptionService twoWayEncryptionService;

    @Autowired
    private EmailingService emailingService;

    @Autowired
    private PasswordEncryptionService passwordEncryptionService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView welcome(HttpServletRequest request) {
        List<HigherEducation> configuredHigherEducation = defaultAuthenticationService.fetchHigherEducation();
        List<String> formHigherEducation = new ArrayList<>();
        if(null != configuredHigherEducation && configuredHigherEducation.size()>0){
            for(HigherEducation e :configuredHigherEducation)
            formHigherEducation.add(e.getEducation());
        } else {
            formHigherEducation.add("");
        }

        List<ViewPreferredCourse> formPreferredCourse = defaultAuthenticationService.getPublicGroupCourses();
        if(null == formPreferredCourse || formPreferredCourse.size()==0){
            formPreferredCourse = new ArrayList<>();
            ViewPreferredCourse v = new ViewPreferredCourse();
            v.setCourseId(0);
            v.setCourseName("No Courses configured ...");
            v.setGroupName("#");
            formPreferredCourse.add(v);
        }
        List<ViewTestimonial> testimonials = defaultAuthenticationService.fetchTestimonials();
        ModelAndView mv = new ModelAndView("site/index");
        mv.addObject("login", new LoginForm());
        mv.addObject("register", new RegisterForm());
        mv.addObject("higherEducations",formHigherEducation);
        mv.addObject("preferredCourses",formPreferredCourse);
        mv.addObject("testimonials",testimonials);
        mv.addObject("signinactive","active");
        mv.addObject("registeractive","");
        mv.addObject("fbLoginUrl",FBConnection.getFBAuthUrl());
        mv.addObject("isReferral","false");
        setLogoutInMV(mv,request);
        return mv;
	}

    @RequestMapping(value = "/referralListener", method = RequestMethod.GET)
    public ModelAndView referralListener(HttpServletRequest request) {
        List<HigherEducation> configuredHigherEducation = defaultAuthenticationService.fetchHigherEducation();
        List<String> formHigherEducation = new ArrayList<>();
        if(null != configuredHigherEducation && configuredHigherEducation.size()>0){
            for(HigherEducation e :configuredHigherEducation)
                formHigherEducation.add(e.getEducation());
        } else {
            formHigherEducation.add("");
        }

        List<ViewPreferredCourse> formPreferredCourse = defaultAuthenticationService.getPublicGroupCourses();
        if(null == formPreferredCourse || formPreferredCourse.size()==0){
            formPreferredCourse = new ArrayList<>();
            ViewPreferredCourse v = new ViewPreferredCourse();
            v.setCourseId(0);
            v.setCourseName("No Courses configured ...");
            v.setGroupName("#");
            formPreferredCourse.add(v);
        }
        List<ViewTestimonial> testimonials = defaultAuthenticationService.fetchTestimonials();
        ModelAndView mv = new ModelAndView("site/index");
        mv.addObject("login", new LoginForm());
        RegisterForm registerForm = new RegisterForm();
        String email = null;
        String referralUserUniqueId = null;
        try{
            email = twoWayEncryptionService.decrypt(request.getParameter("r"));
            referralUserUniqueId = twoWayEncryptionService.decrypt(request.getParameter("e"));
        }catch (Exception e){
            email = "";
            referralUserUniqueId = "";
        }
        registerForm.setEmail(email);
        registerForm.setReferralCode(referralUserUniqueId);
        mv.addObject("register", registerForm);
        mv.addObject("higherEducations",formHigherEducation);
        mv.addObject("preferredCourses",formPreferredCourse);
        mv.addObject("testimonials",testimonials);
        mv.addObject("signinactive","");
        mv.addObject("registeractive","active");
        mv.addObject("isReferral","true");
        setLogoutInMV(mv,request);
        return mv;
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public ModelAndView forgotPassword(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("site/forgot_password");
        String response = request.getParameter("fpResponse");
        if(StringUtils.isNotBlank(response)){
            if(response.equalsIgnoreCase("X")){
                response = "Email Id is not registered. Please re-check.";
            } else if(response.equalsIgnoreCase("Y")){
                response = "Your new password has been emailed to the mentioned email Id.";
            } else if(response.equalsIgnoreCase("Z")){
                response = "Error while sending the email to the provided email Id.";
            }
        }
        mv.addObject("response",response);
        return mv;
    }

    @RequestMapping(value = "/forgotPasswordAction", method = RequestMethod.POST)
    public ModelAndView forgotPasswordAction(HttpServletRequest request, @RequestParam("email") String email) {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmailId(StringUtils.trim(email));
        authenticationRequest.setAuthMode(AuthMode.DEFAULT.toString());
        boolean isRegisteredUser = defaultAuthenticationService.isRegisteredUser(authenticationRequest);
        if(isRegisteredUser){
            String code = RandomStringUtils.randomAlphanumeric(6);
            ResetCode r = new ResetCode();
            r.setEmail(email);
            r.setCode(code);
            defaultAuthenticationService.setCode(r);
            String emailContent = "Hi,<br />"
                    +" The verification code for resetting your password is: <i>" + code + "</i> " +
                    " <br /><br /> Thanks, <br />- The ExamSignal.com Team";

            if(emailingService.email(email,emailContent,"Exam Signal: Reset Password Verification Code")){
                return new ModelAndView("redirect:/resetPassword?email="+email);
            } else {
                return new ModelAndView("redirect:/forgot_password?response=Z");
            }
        } else {
            return new ModelAndView("redirect:/forgot_password?response=X");
        }
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ModelAndView resetPassword(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("site/reset_password");
        String email = request.getParameter("email");
        if(StringUtils.isNotBlank(email)){
            mv.addObject("email",email);
        } else {
            mv.addObject("email","_");
        }
        return mv;
    }


    @RequestMapping(value = "/resetPasswordAction", method = RequestMethod.POST)
    public ModelAndView resetPasswordAction(HttpServletRequest request, @RequestParam("email") String email,
                                            @RequestParam("password") String password, @RequestParam("cpassword") String cpassword,
                                            @RequestParam("code") String code) {

        ModelAndView mv = new ModelAndView("site/reset_password");
        if(StringUtils.isNotBlank(email) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(code)
                && StringUtils.equalsIgnoreCase(password,cpassword)){
            AuthenticationRequest authenticationRequest = new AuthenticationRequest();
            authenticationRequest.setEmailId(StringUtils.trim(email));
            authenticationRequest.setAuthMode(AuthMode.DEFAULT.toString());
            boolean isRegisteredUser = defaultAuthenticationService.isRegisteredUser(authenticationRequest);
            if(isRegisteredUser){
                ResetCode resetCode = new ResetCode();
                resetCode.setEmail(email.trim());
                resetCode.setCode(code.trim());
                boolean codeMatches = defaultAuthenticationService.verifyCode(resetCode);
                if(codeMatches){
                    String encryptedPassword = passwordEncryptionService.getEncryptedPassword(password);
                    defaultAuthenticationService.resetPassword(encryptedPassword, email);
                    ModelAndView m = new ModelAndView("site/success_reset");
                    m.addObject("response", "Password has been successfully reset. Please retry login");
                    return  m;
                } else {
                    mv.addObject("error", "Verification code is invalid");
                }
            } else {
                mv.addObject("error", "Not a Registered user");
            }
        } else {
            mv.addObject("error", "Invalid Request");
        }
        mv.addObject("email",email);
        return  mv;
    }

    private String getLogoutFromSession(HttpServletRequest request){
        if( null != request.getSession().getAttribute(UserProfileEnum.SIGN_OUT.toString())){
            return (String)request.getSession().getAttribute(UserProfileEnum.SIGN_OUT.toString());
        }
        return Constants.NORMAL_USER_SIGN_OUT_LINK;
    }
    private void setLogoutInMV(ModelAndView mv, HttpServletRequest request){
        mv.addObject("logout",getLogoutFromSession(request));
    }

}
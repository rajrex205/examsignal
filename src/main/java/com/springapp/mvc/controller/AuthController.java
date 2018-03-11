package com.springapp.mvc.controller;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.springapp.adaptor.LoginFormToAuthenticationRequest;
import com.springapp.mvc.model.LoginForm;
import com.springapp.mvc.model.RegisterForm;
import com.springapp.orm.hibernate.model.*;
import com.springapp.service.Constants;
import com.springapp.service.DefaultAuthenticationService;
import com.springapp.service.LoginHelper;
import com.springapp.service.RegistrationService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.util.Map;

@Controller
public class AuthController {
    private HttpTransport transport;
    private JsonFactory jsonFactory;
    private final static String GOOGLE_APP_KEY = "369177612653-i94p6hv9s6e9icnu36rducrji9l31v6b.apps.googleusercontent.com";

    @Autowired
    private DefaultAuthenticationService defaultAuthenticationService;
    @Autowired
    private LoginFormToAuthenticationRequest loginFormToAuthenticationRequest;

    @Autowired
    private LoginHelper loginHelper;

    @Autowired
    private RegistrationService registrationService;

    public AuthController(){
        try {
            transport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        jsonFactory = JacksonFactory.getDefaultInstance();
    }
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
        ModelAndView mv = new ModelAndView("loginform");
        mv.addObject("login", new LoginForm());
        mv.addObject("register", new RegisterForm());
		return mv;
	}

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ModelAndView authenticate(HttpServletRequest request,LoginForm f) {
        AuthAccess authAccess = defaultAuthenticationService.isAuthorized(loginFormToAuthenticationRequest.getAuthenticationRequest(f));
        String redirectionUrl = null;
        String typeOfUser = "";
        if(null != authAccess){
            if("user".equalsIgnoreCase(authAccess.getTypeOfUser())){
                UserProfile p = loginHelper.getUserProfile(authAccess.getId());
                redirectionUrl = "userProfile";
                typeOfUser = "user";
                request.getSession().setAttribute(UserProfileEnum.FIRST_NAME.toString(),p.getFirstName());
                request.getSession().setAttribute(UserProfileEnum.LAST_NAME.toString(),p.getLastName());
                request.getSession().setAttribute(UserProfileEnum.USER_ID.toString(),p.getUserId());
                request.getSession().setAttribute(UserProfileEnum.SIGN_OUT.toString(),Constants.NORMAL_USER_SIGN_OUT_LINK);
            } else{
                if("admin".equalsIgnoreCase(authAccess.getTypeOfUser())){
                    AdminProfile p = loginHelper.getAdminProfile(authAccess.getId());
                    redirectionUrl = "admindashboard";
                    typeOfUser = "admin";
                    request.getSession().setAttribute(AdminProfileEnum.UNIQUE_ID.toString(),p.getUniqueId());
                    request.getSession().setAttribute(AdminProfileEnum.DESCRIPTION.toString(),p.getDescription());
                    request.getSession().setAttribute(AdminProfileEnum.COURSE_ROLE.toString(),p.getCourseRole());
                    request.getSession().setAttribute(AdminProfileEnum.SUBJECT_ROLE.toString(),p.getSubjectRole());
                    request.getSession().setAttribute(AdminProfileEnum.FIRST_NAME.toString(),StringUtils.substringBefore(authAccess.getEmail(),"@"));
                    request.getSession().setAttribute(AdminProfileEnum.SIGN_OUT.toString(),Constants.ADMIN_SIGN_OUT_LINK);
                }
            }
            request.getSession().setAttribute(LoginSessionEnum.ID.toString(),authAccess.getId());
            request.getSession().setAttribute(LoginSessionEnum.EMAIL.toString(),authAccess.getEmail());
            request.getSession().setAttribute(LoginSessionEnum.AUTH_MODE.toString(),authAccess.getAuthMode());
            request.getSession().setAttribute(LoginSessionEnum.LOGGED_IN.toString(),true);
            request.getSession().setAttribute(LoginSessionEnum.TYPE_OF_USER.toString(),typeOfUser);
        } else{
            redirectionUrl = ""; // Default Controller->Index page
            request.getSession().setAttribute(LoginSessionEnum.LOGGED_IN.toString(),false);
        }
        return new ModelAndView("redirect:/"+redirectionUrl);
    }

    @RequestMapping(value = "/gauthenticate", method = RequestMethod.POST )
    @ResponseBody
    public String gauthenticate(HttpServletRequest request) {
        boolean loginStatus = false;
        String idTokenString = request.getParameter("idtoken");
        String jsonResponse = null;
        try {
            String g = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + idTokenString;
            URL u = new URL(g);
            URLConnection c = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    c.getInputStream()));
            String inputLine;
            StringBuffer b = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
                b.append(inputLine);
            in.close();
            jsonResponse = b.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in getting Google Login Response. " + e);
        }
        JSONObject json = new JSONObject(jsonResponse);
        String responseGoogleAppKey = (String)json.get("aud");
        String emailId = (String)json.get("email");

        if (StringUtils.equalsIgnoreCase(responseGoogleAppKey,GOOGLE_APP_KEY) && StringUtils.isNotBlank(emailId)) {
            loginStatus = true;

            AuthAccess authAccess = defaultAuthenticationService.getAuthAccessForSocialSignIn(emailId,AuthMode.GOOGLE.toString());
            if(null == authAccess){ //First Time Login using Social SignIn
                //Create the entry in AuthAccess
                registrationService.registerForSocialSignUp(emailId,AuthMode.GOOGLE.toString());
                authAccess = defaultAuthenticationService.getAuthAccessForSocialSignIn(emailId,AuthMode.GOOGLE.toString());
            }
            request.getSession().setAttribute(LoginSessionEnum.ID.toString(),authAccess.getId());
            request.getSession().setAttribute(LoginSessionEnum.EMAIL.toString(),authAccess.getEmail());
            request.getSession().setAttribute(LoginSessionEnum.LOGGED_IN.toString(),true);
            request.getSession().setAttribute(LoginSessionEnum.TYPE_OF_USER.toString(),"user");
            request.getSession().setAttribute(LoginSessionEnum.AUTH_MODE.toString(),authAccess.getAuthMode());
            UserProfile p = loginHelper.getUserProfile(authAccess.getId());
            if(null != p){
                request.getSession().setAttribute(UserProfileEnum.FIRST_NAME.toString(),p.getFirstName());
                request.getSession().setAttribute(UserProfileEnum.LAST_NAME.toString(),p.getLastName());
                request.getSession().setAttribute(UserProfileEnum.USER_ID.toString(),p.getUserId());
            }
            request.getSession().setAttribute(UserProfileEnum.SIGN_OUT.toString(),Constants.GOOGLE_USER_SIGN_OUT_LINK);
        } else {
            request.getSession().setAttribute(LoginSessionEnum.LOGGED_IN.toString(), false);
        }
        return loginStatus ? "true" : "false";
    }

    @RequestMapping(value = "/fbauthenticate", method = RequestMethod.GET)
    public ModelAndView fbauthenticate(HttpServletRequest req) {
        String code = "";
        code = req.getParameter("code");
        if (code == null || code.equals("")) {
            return new ModelAndView("redirect:/");
        }
        String accessToken = FBConnection.getAccessToken(code);
        FBGraph fbGraph = new FBGraph(accessToken);
        String graph = fbGraph.getFBGraph();
        if(StringUtils.isNotBlank(graph)){
            Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
            String fbUserId = fbProfileData.get("id");
            String email = fbProfileData.get("email");
            AuthAccess authAccess = null;
            if(StringUtils.isNotBlank(fbUserId)){
                FacebookAccess facebookAccess = defaultAuthenticationService.fetchFacebookAccessOnFBId(fbUserId);
                String emailId = null;
                if(null == facebookAccess){ // First Time Login
                    emailId = StringUtils.isNotBlank(email) ? email : getDummyEmailForFB(fbUserId);
                    registrationService.registerFacebook(fbUserId,emailId);
                    registrationService.registerForSocialSignUp(emailId,AuthMode.FACEBOOK.toString());
                } else {
                    emailId = facebookAccess.getEmail();
                }
                authAccess = defaultAuthenticationService.getAuthAccessForSocialSignIn(emailId,AuthMode.FACEBOOK.toString());
                req.getSession().setAttribute(LoginSessionEnum.ID.toString(),authAccess.getId());
                req.getSession().setAttribute(LoginSessionEnum.EMAIL.toString(),authAccess.getEmail());
                req.getSession().setAttribute(LoginSessionEnum.LOGGED_IN.toString(),true);
                req.getSession().setAttribute(LoginSessionEnum.AUTH_MODE.toString(),authAccess.getAuthMode());
                req.getSession().setAttribute(LoginSessionEnum.TYPE_OF_USER.toString(),"user");
                UserProfile p = loginHelper.getUserProfile(authAccess.getId());
                if(null != p){
                    req.getSession().setAttribute(UserProfileEnum.FIRST_NAME.toString(),p.getFirstName());
                    req.getSession().setAttribute(UserProfileEnum.LAST_NAME.toString(),p.getLastName());
                    req.getSession().setAttribute(UserProfileEnum.USER_ID.toString(),p.getUserId());
                }
                req.getSession().setAttribute(UserProfileEnum.SIGN_OUT.toString(),Constants.NORMAL_USER_SIGN_OUT_LINK);
                return new ModelAndView("redirect:/userProfile");
            }
        }
        return new ModelAndView("redirect:/");
    }

    private String getDummyEmailForFB(String fbId){
       return fbId+ Constants.FB_FIRST_TIME_DUMMY_AC;
    }
}
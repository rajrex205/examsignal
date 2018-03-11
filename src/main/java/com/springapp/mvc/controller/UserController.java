package com.springapp.mvc.controller;

import com.springapp.mvc.model.*;
import com.springapp.orm.hibernate.model.*;
import com.springapp.service.Constants;
import com.springapp.service.DefaultAuthenticationService;
import com.springapp.service.ReferralEmailingService;
import com.springapp.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserPhotoPath userPhotoPath;

    @Autowired
    private UserPhotoPath fbShareTemplatePhotoPath;

    @Autowired
    private UserPhotoPath fbShareResultPhotoPath;

    @Autowired
    private DefaultAuthenticationService defaultAuthenticationService;

    @Autowired
    private ReferralEmailingService referralEmailingService;

    private static final String DEFAULT_PROFILE_PIC = "default-image.jpg";

    /*@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String viewDashboard(HttpServletRequest request, ModelMap m) {
        boolean isLoggedIn = false;
        String email = null;
        String name = null;
        String authMode = null;
        if(null != request.getSession().getAttribute(LoginSessionEnum.LOGGED_IN.toString())){
            isLoggedIn = (Boolean) request.getSession().getAttribute(LoginSessionEnum.LOGGED_IN.toString());
        }
        if(null != request.getSession().getAttribute(LoginSessionEnum.EMAIL.toString())){
            email = (String) request.getSession().getAttribute(LoginSessionEnum.EMAIL.toString());
        }
        if(null != request.getSession().getAttribute(UserProfileEnum.FIRST_NAME.toString())){
            name = (String) request.getSession().getAttribute(UserProfileEnum.FIRST_NAME.toString());
        }
        if(null != request.getSession().getAttribute(LoginSessionEnum.AUTH_MODE.toString())){
            authMode = (String) request.getSession().getAttribute(LoginSessionEnum.AUTH_MODE.toString());
        }

        if(isLoggedIn){
            if(null == request.getSession().getAttribute(UserProfileEnum.FIRST_NAME.toString())){
                m.addAttribute("isLoggedIn", "Welcome User");
                if(authMode.equalsIgnoreCase(AuthMode.GOOGLE.toString())){
                    m.addAttribute("logout", "<a href=\"#\" onclick=\"signOut();\">Log out</a>");
                } else{
                    m.addAttribute("logout", "<a href='/usersignout'>Log Out</a>");
                }
                m.addAttribute("profilePage", "Please provide some more details about you. <a href='/completeUserProfile'>Click</a>");
                m.addAttribute("email", "Registered Email: " + email);
                m.addAttribute("name", name);
            } else {
                m.addAttribute("isLoggedIn", "Welcome User");
                if(authMode.equalsIgnoreCase(AuthMode.GOOGLE.toString())){
                    m.addAttribute("logout", "<a href=\"#\" onclick=\"signOut();\">Log out</a>");
                } else{
                    m.addAttribute("logout", "<a href='/usersignout'>Log Out</a>");
                }
                m.addAttribute("profilePage", "");
                m.addAttribute("email", "Registered Email: " + email);
                m.addAttribute("name", name);
            }
        } else {
            m.addAttribute("isLoggedIn","User Details not found");
            m.addAttribute("logout", "");
            m.addAttribute("email","");
            m.addAttribute("profilePage", "");
            m.addAttribute("name","");
        }
        return "dashboard";
    }*/

    @RequestMapping(value = "/completeUserProfile", method = RequestMethod.GET)
    public ModelAndView completeUserProfile() {
        return new ModelAndView("profilecompletionform","command",new ProfileCompletionForm());
    }

    @RequestMapping(value = "/gsignout", method = RequestMethod.GET )
    @ResponseBody
    public String gsignout(HttpServletRequest request) {
        if(request.getSession().getAttribute(LoginSessionEnum.LOGGED_IN.toString())==true){
            request.getSession().setAttribute(LoginSessionEnum.LOGGED_IN.toString(),false);
            request.getSession().setAttribute(LoginSessionEnum.ID.toString(),null);
            request.getSession().setAttribute(LoginSessionEnum.EMAIL.toString(),null);
            request.getSession().setAttribute(UserProfileEnum.USER_ID.toString(),null);
            request.getSession().setAttribute(UserProfileEnum.FIRST_NAME.toString(),null);
        }
        return "true";
    }

    @RequestMapping(value = "/usersignout", method = RequestMethod.GET )
    @ResponseBody
    public  ModelAndView userDefaultSignOut(HttpServletRequest request) {
        if(request.getSession().getAttribute(LoginSessionEnum.LOGGED_IN.toString())==true){
            request.getSession().setAttribute(LoginSessionEnum.LOGGED_IN.toString(),false);
            request.getSession().setAttribute(LoginSessionEnum.ID.toString(),null);
            request.getSession().setAttribute(LoginSessionEnum.EMAIL.toString(),null);
            request.getSession().setAttribute(UserProfileEnum.USER_ID.toString(),null);
            request.getSession().setAttribute(UserProfileEnum.FIRST_NAME.toString(),null);
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/userProfile", method = RequestMethod.GET)
    public ModelAndView userProfile(HttpServletRequest request) {
        if(isLoggedIn(request)){
            ModelAndView mv = new ModelAndView("site/user/user_profile");
            ViewUserProfile userProfile = userService.fetchUserProfile(getUserIDFromSession(request));
            if(null == userProfile){
                return new ModelAndView("redirect:/editUserProfile");
            }
            if(null != userProfile && StringUtils.isBlank(userProfile.getFirstName())){
                return new ModelAndView("redirect:/editUserProfile");
            }
            request.getSession().setAttribute(UserProfileEnum.POINTS.toString(),userProfile.getPoints());
            String profilePicFileName = getUserIDFromSession(request)+".";
            String profilePicName = ifFileExistsWithPrefix(userPhotoPath.getPath(),profilePicFileName);
            String profilePic  = StringUtils.isBlank(profilePicName) ? DEFAULT_PROFILE_PIC : profilePicName;

            mv.addObject("profilePicture",profilePic);
            mv.addObject("profile", userProfile);
            setLogoutInMV(mv,request);
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/referFriend", method = RequestMethod.GET)
    public ModelAndView referFriend(HttpServletRequest request) {
        if(isLoggedIn(request)){
            ModelAndView mv = new ModelAndView("site/user/refer_friend");
            mv.addObject("response","");
            setLogoutInMV(mv, request);
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/referFriendAction", method = RequestMethod.POST)
    ModelAndView referFriendAction(HttpServletRequest request, @RequestParam("email") String email) {
        if(isLoggedIn(request)){
            ModelAndView mv = new ModelAndView("site/user/refer_friend");
            String response = "";
            String senderUserName = (String) request.getSession().getAttribute(UserProfileEnum.FIRST_NAME.toString());
            String senderUniqueId = (String) request.getSession().getAttribute(UserProfileEnum.USER_ID.toString());
            if(StringUtils.isNotBlank(email)){
                if(referralEmailingService.sendReferralEmail(senderUserName,senderUniqueId,email)){
                    response="Request sent !";
                } else{
                    response="Request Failed. Please contact ExamSignal Support division.";
                }
            }
            setLogoutInMV(mv,request);
            mv.addObject("response",response);
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

        @RequestMapping(value = "/results", method = RequestMethod.GET)
    public ModelAndView results(HttpServletRequest request) {
        if(isLoggedIn(request)){
            ModelAndView mv = new ModelAndView("site/user/results");
            List<ExamResultView> examResultViewList = userService.getUserExamResult(getUserIDFromSession(request));
            List<CourseResultView> courseResultViewList = null;

            if(null != examResultViewList){
                courseResultViewList = new ArrayList<>();
                Set<Long> distinctCourseId = new HashSet<>();
                for(ExamResultView e: examResultViewList){
                    distinctCourseId.add(e.getCourseId());
                }
                for(Long courseId: distinctCourseId){
                    CourseResultView c = new CourseResultView();
                    c.setCourseId(courseId);
                    List<ExamResultView> e1 = new ArrayList<>();
                    c.setExamResultViews(e1);
                    c.setActiveAttribute("");
                    for(ExamResultView e: examResultViewList){
                        if(e.getCourseId()==courseId){
                            e1.add(e);
                        }
                    }
                    c.setCourseName(c.getExamResultViews().get(0).getCourseName());
                    courseResultViewList.add(c);
                }
            }
            if(null != courseResultViewList && courseResultViewList.size()>0){
                courseResultViewList.get(0).setActiveAttribute("active");
            }
            mv.addObject("examList",courseResultViewList);
            mv.addObject("offer", userService.fetchOffer());
            setLogoutInMV(mv,request);

            ResultPopup resultPopup = null;
            FBSharePopupDetail fbSharePopupDetail = null;
            if(null != request.getParameter("scoreid")){
                long scoreId = Long.parseLong(request.getParameter("scoreid"));
                UserExam exam  = userService.getResultPopup(scoreId);
                fbSharePopupDetail = userService.getFBSharePopup(scoreId);
                if(exam != null){
                    resultPopup = new ResultPopup();
                    resultPopup.setScoreId(scoreId);
                    resultPopup.setTotalMarks(String.valueOf(exam.getMaxMarks()));
                    resultPopup.setMarksSecured(String.valueOf(exam.getTotalMarks()));
                    int percentage = (exam.getTotalMarks() * 100) / exam.getMaxMarks();
                    resultPopup.setPercentage(String.valueOf(percentage));
                    resultPopup.setPercentageDescription(percentageDescription(percentage));
                    resultPopup.setTotalTime(String.valueOf(exam.getExamDuration()));
                    Calendar startTime = Calendar.getInstance();
                    startTime.setTimeInMillis(exam.getStartTime().getTime());
                    Calendar endTime = Calendar.getInstance();
                    endTime.setTimeInMillis(exam.getEndTime().getTime());
                    long diff = endTime.getTimeInMillis() - startTime.getTimeInMillis();
                    long dminutes = diff / (60 * 1000);
                    resultPopup.setTimeTaken(String.valueOf(dminutes));
                }
                if(null != fbSharePopupDetail){
                        fbSharePopupDetail.setPercentage(resultPopup.getPercentage());
                    fbSharePopupDetail.setPercentageDescription(resultPopup.getPercentageDescription());
                    try {
                        BufferedImage TEMPLATE_IMAGE = ImageIO.read(new File(fbShareTemplatePhotoPath.getPath()+"/FB-Popup-Template.png"));
                        Graphics g = TEMPLATE_IMAGE.getGraphics();
                        g.setFont(g.getFont().deriveFont(20f));
                        g.setColor(Color.decode("#7F7D7D"));
                        g.drawString(fbSharePopupDetail.getTotalPoints(), 355, 75);
                        g.drawString(fbSharePopupDetail.getUserName()+"'s score in "+fbSharePopupDetail.getCourseName()+" "+fbSharePopupDetail.getExamName(), 15, 125);
                        g.drawString(fbSharePopupDetail.getMarksSecured()+" marks out of "+fbSharePopupDetail.getTotalMarks(), 15, 170);
                        g.drawString(fbSharePopupDetail.getPercentageDescription(), 15, 220);
                        g.drawString(fbSharePopupDetail.getUserName()+" scored "+fbSharePopupDetail.getPercentage()+"%", 15, 270);
                        g.dispose();
                        ImageIO.write(TEMPLATE_IMAGE, "png", new File(fbShareResultPhotoPath.getPath()+"/"+scoreId+".png"));
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            mv.addObject("popupdetails", resultPopup);
            mv.addObject("fbshare", fbSharePopupDetail);
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/editUserProfile", method = RequestMethod.GET)
    public ModelAndView editProfile(HttpServletRequest request) {
        if(isLoggedIn(request)){
            ViewUserProfile userProfile = userService.fetchUserProfile(getUserIDFromSession(request));
            EditUserProfileForm editForm = new EditUserProfileForm();
            String points = "0";
            String uniqueID = "Pending ...";
            if(null != userProfile){
                String email = userProfile.getEmail();
                String authMode = (String)request.getSession().getAttribute(LoginSessionEnum.AUTH_MODE.toString());
                if(StringUtils.equalsIgnoreCase(AuthMode.FACEBOOK.toString(),authMode)){
                    if(StringUtils.contains(email, Constants.FB_FIRST_TIME_DUMMY_AC)){
                        email = "";
                    }
                }
                editForm.setEmail(email);
                editForm.setFirstName(userProfile.getFirstName());
                editForm.setLastName(userProfile.getLastName());

                editForm.setAddress(userProfile.getAddress());
                editForm.setGender(userProfile.getGender());
                editForm.setPhone(userProfile.getPhone());

                //Non Editable Fields from fetch results but to be displayed on UI
                points = userProfile.getPoints();
                uniqueID = userProfile.getUniqueId();
            } else {
                //For Social Login  - First Time
                editForm.setEmail((String)request.getSession().getAttribute(LoginSessionEnum.EMAIL.toString()));
            }

            editForm.setId((Long)request.getSession().getAttribute(LoginSessionEnum.ID.toString()));
            // Latest List of Preferred Courses (Public Group Courses)
            List<ViewPreferredCourse> formPreferredCourse = defaultAuthenticationService.getPublicGroupCourses();
            if(null == formPreferredCourse || formPreferredCourse.size()==0){
                formPreferredCourse = new ArrayList<>();
                ViewPreferredCourse v = new ViewPreferredCourse();
                v.setCourseId(0);
                v.setCourseName("No Courses configured ...");
                v.setGroupName("#");
                formPreferredCourse.add(v);
            }
            // Latest List of Higher Education List
            List<HigherEducation> configuredHigherEducation = defaultAuthenticationService.fetchHigherEducation();
            List<String> formHigherEducation = new ArrayList<>();
            if(null != configuredHigherEducation && configuredHigherEducation.size()>0){
                for(HigherEducation e :configuredHigherEducation)
                    formHigherEducation.add(e.getEducation());
            } else {
                formHigherEducation.add("");
            }
            ModelAndView mv = new ModelAndView("site/user/edit_profile");
            mv.addObject("uniqueID",uniqueID);
            mv.addObject("points",points);
            mv.addObject("editForm", editForm);
            mv.addObject("formPreferredCourse", formPreferredCourse);
            mv.addObject("formHigherEducation", formHigherEducation);
            setLogoutInMV(mv,request);
            String errorMessage= "";
            if(null!= request.getParameter("error")){
                String errorCode = request.getParameter("error");
                if("1".equals(errorCode)){
                    errorMessage = "Email Id already exists ...";
                }
            }
            mv.addObject("error",errorMessage);

            if("".equalsIgnoreCase(editForm.getEmail())){
                mv.addObject("emailReadOnly", "false");
            } else{
                mv.addObject("emailReadOnly", "true");
            }
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/editUserProfileAction", method = RequestMethod.POST)
    public ModelAndView editUserProfileAction(HttpServletRequest request,EditUserProfileForm f) {
        if(null != f && f.isValidRequest()){
            f.setId(getUserIDFromSession(request));
            f.setPreferredCoursesList();
            String authMode = (String)request.getSession().getAttribute(LoginSessionEnum.AUTH_MODE.toString());
            //Check if updated emailID already exists for FB case
            if(StringUtils.equalsIgnoreCase(AuthMode.FACEBOOK.toString(),authMode)){
                AuthAccess authAccess = defaultAuthenticationService.getAuthAccessForSocialSignIn(f.getEmail(),authMode);
                if(null != authAccess){
                    ModelAndView m = new ModelAndView("redirect:/editUserProfile?error=1");
                    return m;
                }
            }
            userService.updateProfile(f);
            if(StringUtils.equalsIgnoreCase(AuthMode.FACEBOOK.toString(),authMode)){
                String inSessionEmailId = (String)request.getSession().getAttribute(LoginSessionEnum.EMAIL.toString());
                String updatedEmailId = f.getEmail();
                if(!StringUtils.equalsIgnoreCase(inSessionEmailId,updatedEmailId)){ //First Time Registration setup
                    request.getSession().setAttribute(LoginSessionEnum.EMAIL.toString(), f.getEmail());
                    defaultAuthenticationService.updateFacebookAccessForEmailId(inSessionEmailId,updatedEmailId);
                    defaultAuthenticationService.updateAuthAccessForEmailId(inSessionEmailId,updatedEmailId);
                }
            }
            request.getSession().setAttribute(UserProfileEnum.FIRST_NAME.toString(),f.getFirstName());
            request.getSession().setAttribute(UserProfileEnum.LAST_NAME.toString(),f.getLastName());
            return new ModelAndView("redirect:/userProfile");
        } else{
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/viewGroups", method = RequestMethod.GET)
    public ModelAndView viewGroups(HttpServletRequest request) {
        if(isLoggedIn(request)){
            ModelAndView mv = new ModelAndView("site/user/groups");
            List<ViewSubscribedGroup> viewSubscribedGroups = userService.fetchActiveGroupsForUser(getUserIDFromSession(request));
            List<Course> addableCourses = userService.fetchAddableCoursesForUser(getUserIDFromSession(request));
            List<ViewPreferredCourse> preferredCourses = userService.fetchAllActivePreferredCoursesForUser(getUserIDFromSession(request));
            mv.addObject("activeGroups",viewSubscribedGroups);
            mv.addObject("subscribedGroupResponse",viewSubscribedGroups);
            mv.addObject("addableCourses",addableCourses);
            mv.addObject("addCourseForm",new AddUserCourseForm());
            mv.addObject("preferredCourses",preferredCourses);
            mv.addObject("subscribeResponse",getSubscriptionDetailResponseMessage(request));
            setLogoutInMV(mv,request);
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/subscribeGroupAction", method = RequestMethod.POST)
    ModelAndView subscribeGroupAction(HttpServletRequest request, @RequestParam("group") String group) {
        if(isLoggedIn(request)){
            String response = "0";
            if(StringUtils.isNotBlank(group)){
                //Check if request already sent
                Long groupId = userService.fetchGroupID(group);
                if(null == groupId){
                    response = "1";
                } else {
                    String subscriptionStatus = userService.fetchSubscriptionStatus(getUserIDFromSession(request),groupId);
                    if(StringUtils.equalsIgnoreCase(SubscriptionStatus.REQUESTED.toString(),subscriptionStatus)){
                        response = "2";
                    } else if(StringUtils.equalsIgnoreCase(SubscriptionStatus.APPROVED.toString(),subscriptionStatus)){
                        response = "3";
                    } else {
                        userService.createSubscriptionRequest(getUserIDFromSession(request),groupId);
                        response = "4";
                    }
                }
            }
            ModelAndView mv = new ModelAndView("redirect:/viewGroups?subscriptionResponseCode="+response);
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/deleteGroup", method = RequestMethod.GET)
    public ModelAndView deleteGroup(HttpServletRequest request) {
        if(request.getParameter("gid") != null){
            Long groupId = Long.parseLong(request.getParameter("gid"));
            if(null != groupId){
                userService.deleteGroupForUser(getUserIDFromSession(request),groupId);
            }
        }
        return new ModelAndView("redirect:/viewGroups");
    }

    @RequestMapping(value = "/addUserCourse", method = RequestMethod.POST)
    ModelAndView addUserCourse(HttpServletRequest request, AddUserCourseForm f) {
        if(isLoggedIn(request)){
            ModelAndView mv = new ModelAndView("redirect:/viewGroups");
            userService.addMoreCoursesToUser(getUserIDFromSession(request),f.getCourseIDs());
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }


    private boolean isLoggedIn(HttpServletRequest request){
        String typeOfUser =  (String)request.getSession().getAttribute(LoginSessionEnum.TYPE_OF_USER.toString());
        boolean isUser = StringUtils.equalsIgnoreCase("user",typeOfUser);
        return (request.getSession().getAttribute(LoginSessionEnum.LOGGED_IN.toString())==true) && isUser;
    }

    @RequestMapping(value = "/unsubscribeCourse", method = RequestMethod.GET)
    public ModelAndView unsubscribeCourse(HttpServletRequest request) {
        if(request.getParameter("cid") != null){
            Long courseId = Long.parseLong(request.getParameter("cid"));
            if(null != courseId){
                userService.unsubscribeCourseForUser(getUserIDFromSession(request), courseId);
            }
        }
        return new ModelAndView("redirect:/viewGroups");
    }

    @RequestMapping(value = "/selectExam", method = RequestMethod.GET)
    public ModelAndView selectExam(HttpServletRequest request) {
        ModelAndView m = new ModelAndView("site/user/select-exam");

        //Prepare Exam List
        List<SelectExamView> allExamsForUser = userService.fetchAllExamsForUser(getUserIDFromSession(request));
        Set<String> distinctCourses = new HashSet<>();
        ViewExams allExams = new ViewExams();


        if(null!= allExamsForUser && allExamsForUser.size()>0){
            Map<String,List<SelectExamView>> map= allExams.getExams();
            for(SelectExamView e: allExamsForUser){
                distinctCourses.add(e.getCourseName());
                if(StringUtils.isNotBlank(e.getCourseName())){
                    List<SelectExamView> list = map.get(e.getCourseName());
                    if(null == list){
                        list = new ArrayList<>();
                        map.put(e.getCourseName(),list);
                    }
                    list.add(e);
                }
            }
        }
        //Dummy Data for UI Rendering
        /*Map<String,List<SelectExamView>> map= allExams.getExams();
        List<SelectExamView> IITList = new ArrayList<>();
        List<SelectExamView> MBAList = new ArrayList<>();
        distinctCourses.add("IIT");
        distinctCourses.add("MBA");

        SelectExamView s1 = new SelectExamView();
        s1.setExamId(1);
        s1.setCourseId(1);
        s1.setCourseName("IIT");
        s1.setExamName("IIT_1");
        s1.setTypeOfExam("PRACTICE");
        s1.setIsExamAttempted("Y");

        SelectExamView s2 = new SelectExamView();
        s2.setExamId(2);
        s2.setCourseId(1);
        s2.setCourseName("IIT");
        s2.setExamName("IIT_2");
        s2.setTypeOfExam("EXAM");
        s2.setIsExamAttempted("N");

        SelectExamView s3 = new SelectExamView();
        s3.setExamId(3);
        s3.setCourseId(1);
        s3.setCourseName("IIT");
        s3.setExamName("IIT_3");
        s3.setTypeOfExam("EXAM");
        s3.setIsExamAttempted("Y");

        SelectExamView s4 = new SelectExamView();
        s4.setExamId(4);
        s4.setCourseId(1);
        s4.setCourseName("IIT");
        s4.setExamName("IIT_4");
        s4.setTypeOfExam("PRACTICE");
        s4.setIsExamAttempted("N");

        SelectExamView s5 = new SelectExamView();
        s5.setExamId(5);
        s5.setCourseId(2);
        s5.setCourseName("MBA");
        s5.setExamName("MBA_1");
        s5.setTypeOfExam("PRACTICE");
        s5.setIsExamAttempted("Y");

        SelectExamView s6 = new SelectExamView();
        s6.setExamId(6);
        s6.setCourseId(2);
        s6.setCourseName("MBA");
        s6.setExamName("MBA_2");
        s6.setTypeOfExam("EXAM");
        s6.setIsExamAttempted("N");

        IITList.add(s1);
        IITList.add(s2);
        IITList.add(s3);
        IITList.add(s4);
        MBAList.add(s5);
        MBAList.add(s6);

        map.put("IIT",IITList);
        map.put("MBA",MBAList);
        */
        m.addObject("allExams",allExams);
        m.addObject("distinctCourses",new ArrayList<String>(distinctCourses));
        m.addObject("form",new SelectExamForm());
        setLogoutInMV(m,request);
        return m;
    }

    @RequestMapping(value = "/examDetail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ExamDetailsPopup getExamDetails(HttpServletRequest request, @RequestBody ExamDetailsPopup e) {
        if(isLoggedIn(request) && null!=e) {
            Long examId = Long.valueOf(e.getExamId());
            if(isAuthorizedExamAccess(request,examId)) {
                ExamDetailsPopup examDetailsPopup = userService.getExamDetails(examId);
                return examDetailsPopup;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/exam", method = RequestMethod.GET)
    public ModelAndView takeExam(HttpServletRequest request) {
        if(isLoggedIn(request)){
            Long examIdInSession = getExamIDFromSession(request);
            if(null == examIdInSession){  /// Expect an exam id in request then
                if(StringUtils.isBlank(request.getParameter("examid"))) {
                    return new ModelAndView("redirect:/selectExam?error=ExamIdNotSelected"); // ExamId neither in request nor in session
                } else {
                    long examIdInRequest = Long.parseLong(request.getParameter("examid"));
                    if(isAuthorizedExamAccess(request,examIdInRequest)){
                        clearExamSession(request);
                        request.getSession().setAttribute(ExamRelatedEnum.EXAM_ID.toString(), examIdInRequest);
                        ModelAndView mv = new ModelAndView("redirect:/exam");
                        return mv;
                    } else {
                        return new ModelAndView("redirect:/selectExam?error=UserNotAuthorizedForExam");
                    }
                }
            } else { // Exam Page logic
                ModelAndView mv = new ModelAndView("site/user/exam");
                Object existingExamSessionData = request.getSession().getAttribute(ExamRelatedEnum.OTHER_DETAILS.toString());
                List<ExamSubjectView> examSubjectViews = null;
                if(null == existingExamSessionData){
                    List<Subject> subjects = userService.getSubjectsForExam(examIdInSession);
                    if(null != subjects && subjects.size()>0){
                        examSubjectViews = new ArrayList<>();
                        Map<Long,ExamSession> map = new HashMap<>();
                        for(Subject s: subjects){
                            ExamSubjectView v = new ExamSubjectView();
                            v.setSubjectId(s.getId());
                            v.setCourseID(s.getCourseID());
                            v.setSubjectName(s.getSubjectName());
                            int questionCountForSubject = userService.getQuestionCountForSubject(s.getId(),getExamIDFromSession(request));
                            Question firstQuestion = userService.getFirstQuestionOfExamForSubject(s.getId(), getExamIDFromSession(request));
                            firstQuestion.setAnswer(null);
                            v.setFirstQuestion(firstQuestion);
                            ExamSession e = new ExamSession();
                            e.setSubjectId(s.getId());
                            e.setNumberOfQuestions(questionCountForSubject);
                            e.setSubjectName(s.getSubjectName());
                            String[] attemptStatus = new String[questionCountForSubject];
                            for(int j=0;j<questionCountForSubject;j++){
                                attemptStatus[j] = QuestionAttemptStatus.NEW.toString();
                            }
                            e.setQuestionAttemptStatus(attemptStatus);
                            e.setOptionSelected(new int[questionCountForSubject]);
                            e.setLastQuestionAttempted(1);
                            map.put(s.getId(),e);
                            examSubjectViews.add(v);
                        }
                        request.getSession().setAttribute(ExamRelatedEnum.EXAM_SESSION.toString(),map);
                        Exam exam = userService.fetchExam(getExamIDFromSession(request));
                        request.getSession().setAttribute(ExamRelatedEnum.DURATION.toString(),exam.getDuration());
                        request.getSession().setAttribute(ExamRelatedEnum.EXAM_NAME.toString(),exam.getExamName());
                        request.getSession().setAttribute(ExamRelatedEnum.EXAM_TYPE.toString(),exam.getExamType());
                        examSubjectViews.get(0).setByDefaultActiveCode("active");
                    } else {
                        return new ModelAndView("redirect:/selectExam?error=SubjectConfigIssue");
                    }
                } else {
                    examSubjectViews = (List<ExamSubjectView>)existingExamSessionData;
                }

                mv.addObject("subjects",examSubjectViews);
                setLogoutInMV(mv, request);
                initExam(request, examSubjectViews);
                return mv;
            }
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value="/submitThisQuestion", method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public QuestionForm submitThisQuestion(HttpServletRequest request, @RequestBody QuestionForm questionForm) {
        if(isLoggedIn(request) ){
            QuestionForm response = new QuestionForm();
            response.setQuestionId(questionForm.getQuestionId());
            response.setSelectedOption(questionForm.getSelectedOption());
            response.setSubjectId(questionForm.getSubjectId());
            String questionId = questionForm.getQuestionId();
            if(!isTimeOver(request) && !isSubmittedExam(request) ){
                if(StringUtils.isNotBlank(questionId)){
                    Question q = userService.getQuestion(Integer.parseInt(questionId),getExamIDFromSession(request),Long.parseLong(questionForm.getSubjectId()));
                    int pointScored =  StringUtils.equalsIgnoreCase(questionForm.getSelectedOption(),q.getAnswer().toString()) ? q.getMarks() : 0;
                    userService.updateScore(getScoreIdFromSession(request),q.getId(),pointScored);
                    Map<Long,ExamSession> map = (HashMap)request.getSession().getAttribute(ExamRelatedEnum.EXAM_SESSION.toString());
                    ExamSession e = map.get(q.getSubjectId());
                    e.getQuestionAttemptStatus()[q.getQuestionNumber()-1] = QuestionAttemptStatus.ATTEMPTED.toString();
                    e.getOptionSelected()[q.getQuestionNumber()-1] =  Integer.parseInt(questionForm.getSelectedOption());
                    e.setLastQuestionAttempted(q.getQuestionNumber());
                    response.setResponse("Question "+q.getQuestionNumber()+" saved Successfully");
                } else {
                    response.setResponse("Invalid Request");
                }
            } else {
                response.setResponse("Time Over");
            }
            return response;
        }
        return null;
    }

    @RequestMapping(value="/getQuestion", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Question getQuestion(HttpServletRequest request, @RequestBody GetQuestion question) {
        if(isLoggedIn(request) && !isSubmittedExam(request) && !isTimeOver(request) && null!=question && StringUtils.isNotBlank(question.getQuestionId()) && StringUtils.isNotBlank(question.getSubjectId())){
            Map<Long,ExamSession> map = (HashMap)request.getSession().getAttribute(ExamRelatedEnum.EXAM_SESSION.toString());
            Question q = userService.getQuestion(Integer.parseInt(question.getQuestionId()),getExamIDFromSession(request),Long.parseLong(question.getSubjectId()));
            ExamSession e = map.get(Long.parseLong(question.getSubjectId()));
            if(null != q){
                e.setLastQuestionAttempted(q.getQuestionNumber());
                q.setAnswer(null);
            }
            return q;
        }
        return null;
    }

    @RequestMapping(value="/reviewQuestionLater", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseJson reviewQuestionLater(HttpServletRequest request, @RequestBody GetQuestion question) {
        String message = "";
        if(isLoggedIn(request) && !isSubmittedExam(request) && !isTimeOver(request)  && null!=question && StringUtils.isNotBlank(question.getQuestionId()) && StringUtils.isNotBlank(question.getSubjectId())){
            Map<Long,ExamSession> map = (HashMap)request.getSession().getAttribute(ExamRelatedEnum.EXAM_SESSION.toString());
            ExamSession e = map.get(Long.parseLong(question.getSubjectId()));
            int questionNumber = Integer.parseInt(question.getQuestionId());
            int maxQuestionForThisSubject = e.getNumberOfQuestions();
            if(questionNumber<=maxQuestionForThisSubject) { //validation
                e.getQuestionAttemptStatus()[questionNumber-1]= QuestionAttemptStatus.REVIEW.toString();
                message = "Marked for Review";
            }
            return new ResponseJson(message,question.getSubjectId());
        }
        return null;
    }

    @RequestMapping(value="/getNextQuestion", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Question getNextQuestion(HttpServletRequest request, @RequestBody String subjectId) {
        if(isLoggedIn(request)  && !isSubmittedExam(request) && !isTimeOver(request) && StringUtils.isNotBlank(subjectId)){
            Map<Long,ExamSession> map = (HashMap)request.getSession().getAttribute(ExamRelatedEnum.EXAM_SESSION.toString());
            ExamSession e = map.get(Long.parseLong(subjectId));
            int lastAttemptedQuestion =  e.getLastQuestionAttempted();
            Question q = userService.getNextQuestion(lastAttemptedQuestion,getExamIDFromSession(request),Long.parseLong(subjectId));
            if(null != q){
                e.setLastQuestionAttempted(q.getQuestionNumber());
                q.setAnswer(null);
            }
            return q;
        }
        return null;
    }
    @RequestMapping(value="/getPreviousQuestion", method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Question getPreviousQuestion(HttpServletRequest request, @RequestBody String subjectId) {
        if(isLoggedIn(request) && !isSubmittedExam(request) && !isTimeOver(request) && StringUtils.isNotBlank(subjectId)){
            Map<Long,ExamSession> map = (HashMap)request.getSession().getAttribute(ExamRelatedEnum.EXAM_SESSION.toString());
            ExamSession e = map.get(Long.parseLong(subjectId));
            int lastAttemptedQuestion =  e.getLastQuestionAttempted();
            Question q = userService.getPreviousQuestion(lastAttemptedQuestion,getExamIDFromSession(request),Long.parseLong(subjectId));
            if(null != q){
                e.setLastQuestionAttempted(q.getQuestionNumber());
                q.setAnswer(null);
            }
            return q;
        }
        return null;
    }


    @RequestMapping(value="/getTimePassed", method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ExamTime getTimePassed(HttpServletRequest request) {
        if(isLoggedIn(request)  && !isSubmittedExam(request)){
            Date startTime = (Date) request.getSession().getAttribute(ExamRelatedEnum.START_TIME.toString());
            Date currentTime = getCurrentDate();
            long diffMilliSec = currentTime.getTime() - startTime.getTime();
            long diffSec = diffMilliSec / 1000;
            long min = diffSec/60;
            long sec = diffSec % 60;
            return new ExamTime(min,sec);
        } else {
            return  null;
        }
    }

    @RequestMapping(value = "/submitExam", method = RequestMethod.POST)
    public ModelAndView submitExam(HttpServletRequest request, @RequestParam("examiid") String examId) {
        if (isLoggedIn(request)  && !isSubmittedExam(request) ) {
            long scoreId = getScoreIdFromSession(request);
            Points p = new Points();
            p.setUserId(getUserIDFromSession(request));
            p.setReasonCode(PointsCategory.EXAM.getReason());
            p.setReasonDescription(getExamIDFromSession(request).toString());
            userService.saveMarksAndPoints(scoreId, p);
            clearExamSession(request);
            ModelAndView mv = new ModelAndView("redirect:/results?scoreid="+scoreId);
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/addPhoto", method = RequestMethod.POST)
    public ModelAndView uploadFileHandler(HttpServletRequest request, @RequestParam("photo") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                String newFileName=getUserIDFromSession(request)+"."+extension;

                // Creating the directory to store file

                File dir = new File(userPhotoPath.getPath());
                if (!dir.exists())
                    dir.mkdirs();
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + newFileName);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                return new ModelAndView("redirect:/userProfile");
            } catch (Exception e) {
                return new ModelAndView("redirect:/userProfile");
            }
        } else {
            return new ModelAndView("redirect:/userProfile");
        }
    }

    private Long getUserIDFromSession(HttpServletRequest request){
        Object o = request.getSession().getAttribute(LoginSessionEnum.ID.toString());
        if(null != o){
            return (long) o;
        }
        return null;
    }

    private Long getExamIDFromSession(HttpServletRequest request){
        Object o = request.getSession().getAttribute(ExamRelatedEnum.EXAM_ID.toString());
        if(null != o){
            return (long) o;
        }
        return null;
    }

    private boolean isAuthorizedExamAccess(HttpServletRequest request, long examId){
        boolean result = false;
        List<SelectExamView> allExamsForUser = userService.fetchAllExamsForUser(getUserIDFromSession(request));
        if(null != allExamsForUser){
            for(SelectExamView e: allExamsForUser){
                if(e.getExamId() == examId){
                    result=true;
                    if(StringUtils.equalsIgnoreCase(e.getTypeOfExam(),ExamType.EXAM.toString()) && StringUtils.equalsIgnoreCase(e.getIsExamAttempted(),"Y")){
                        result=false;
                    }
                    break;
                }
            }
        }
        return result;
    }


    private String getSubscriptionDetailResponseMessage(HttpServletRequest request){
        String responseMessage = "";
        if(StringUtils.isNotBlank(request.getParameter("subscriptionResponseCode"))){
            int responseCode = Integer.parseInt(request.getParameter("subscriptionResponseCode"));
            if(responseCode == 1){
                responseMessage = "Invalid Group Name. This group does not exists.";
            } else if(responseCode ==2){
                responseMessage="Request already sent to the Group Admin, awaiting Admin's response.";
            } else if(responseCode==3){
                responseMessage = "You are already subscribed to this group.";
            } else if(responseCode==4){
                responseMessage = "Request sent successfully to Group Admin";
            }
        }
        return  responseMessage;
    }

    private void clearExamSession(HttpServletRequest request){
        request.getSession().setAttribute(ExamRelatedEnum.EXAM_ID.toString(),null);
        request.getSession().setAttribute(ExamRelatedEnum.QUESTION_ID.toString(),null);
        request.getSession().setAttribute(ExamRelatedEnum.OTHER_DETAILS.toString(), null);
        request.getSession().setAttribute(ExamRelatedEnum.START_TIME.toString(),null);
        request.getSession().setAttribute(ExamRelatedEnum.SCORE_ID.toString(),null);
        request.getSession().setAttribute(ExamRelatedEnum.EXAM_SESSION.toString(),null);
    }

    private Date getCurrentDate(){
        Calendar cal = Calendar.getInstance();
        TimeZone z = TimeZone.getTimeZone("Asia/Calcutta");
        cal.setTimeZone(z);
        return cal.getTime();
    }

    private void initExam(HttpServletRequest request, List<ExamSubjectView> list){
        Object examStartTimeObject = request.getSession().getAttribute(ExamRelatedEnum.START_TIME.toString());
        if(null == examStartTimeObject){
            Date examStartTime = getCurrentDate();
            request.getSession().setAttribute(ExamRelatedEnum.START_TIME.toString(),examStartTime);
            request.getSession().setAttribute(ExamRelatedEnum.OTHER_DETAILS.toString(),list);
            long scoreId = userService.setUpForStartExam(getExamIDFromSession(request),getUserIDFromSession(request),examStartTime);
            request.getSession().setAttribute(ExamRelatedEnum.SCORE_ID.toString(),scoreId);
        }
    }

    private String getLogoutFromSession(HttpServletRequest request){
        if( null != request.getSession().getAttribute(UserProfileEnum.SIGN_OUT.toString())){
            return (String)request.getSession().getAttribute(UserProfileEnum.SIGN_OUT.toString());
        }
        return Constants.NORMAL_USER_SIGN_OUT_LINK;
    }

    private long getScoreIdFromSession(HttpServletRequest request){
        if( null != request.getSession().getAttribute(ExamRelatedEnum.SCORE_ID.toString())){
            return (long)request.getSession().getAttribute(ExamRelatedEnum.SCORE_ID.toString());
        }
        return -1;
    }

    private void setLogoutInMV(ModelAndView mv, HttpServletRequest request){
        mv.addObject("logout",getLogoutFromSession(request));
    }

    private boolean isTimeOver(HttpServletRequest request){
        String examType = (String) request.getSession().getAttribute(ExamRelatedEnum.EXAM_TYPE.toString());
        if(StringUtils.equalsIgnoreCase(examType,ExamType.EXAM.toString())){
        Date examStartDate = (Date) request.getSession().getAttribute(ExamRelatedEnum.START_TIME.toString());
        int duration = (Integer) request.getSession().getAttribute(ExamRelatedEnum.DURATION.toString());
        Date currentDate = getCurrentDate();
        Date maxDate = DateUtils.addMinutes(examStartDate,duration);
        return currentDate.after(maxDate);
        }else {
            return false; //Practice Test
        }
    }
    private boolean isSubmittedExam(HttpServletRequest request){
        return request.getSession().getAttribute(ExamRelatedEnum.EXAM_ID.toString()) == null;
    }
    private String ifFileExistsWithPrefix(String directory, String prefix){
        File dir = new File(directory);
        File[] files = null;
        files = dir.listFiles((FileFilter) new PrefixFileFilter(prefix, IOCase.SENSITIVE));
        return (null != files &&  files.length ==1) ? files[0].getName() : null;
    }

    private String percentageDescription(int percentage){
        if(percentage<=59){
            return "Average attempt, we appreciate your effort";
        }
        if(percentage<=69){
            return "Amazing Effort";
        }
        if(percentage<=79){
            return "Superb!";
        }
        if(percentage<=89){
            return "You’re great";
        }
        if(percentage<=99){
            return "You’re  excellent";
        }else {
            return "You're genius";
        }
    }

}
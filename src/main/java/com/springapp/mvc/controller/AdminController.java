package com.springapp.mvc.controller;

import com.springapp.mvc.model.*;
import com.springapp.orm.hibernate.model.*;
import com.springapp.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    final private static String ADMIN_DASHBOARD = "admindashboard";

    @RequestMapping(value = "/"+ADMIN_DASHBOARD, method = RequestMethod.GET)
    public ModelAndView viewDashboard(HttpServletRequest request) {
        if(isLoggedIn(request)){
            String description="";
            String uniqueId="";
            if(null != request.getSession().getAttribute(AdminProfileEnum.DESCRIPTION.toString())){
                description = (String) request.getSession().getAttribute(AdminProfileEnum.DESCRIPTION.toString());
            }
            if(null != request.getSession().getAttribute(AdminProfileEnum.UNIQUE_ID.toString())){
                uniqueId = (String) request.getSession().getAttribute(AdminProfileEnum.UNIQUE_ID.toString());
            }
            ModelAndView m = new ModelAndView(ADMIN_DASHBOARD);
            m.addObject("isLoggedIn","Welcome Admin");
            m.addObject("uniqueid",uniqueId);
            m.addObject("description",description);
            m.addObject("email",request.getSession().getAttribute(LoginSessionEnum.EMAIL.toString()));
            m.addObject("email",request.getSession().getAttribute(LoginSessionEnum.EMAIL.toString()));
            m.addObject("courseRole",request.getSession().getAttribute(AdminProfileEnum.COURSE_ROLE.toString()));
            m.addObject("subjectRole",request.getSession().getAttribute(AdminProfileEnum.COURSE_ROLE.toString()));
            m.addObject("logout", "<a href='/adminsignout'>Log Out</a>");
            return m;
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/adminsignout", method = RequestMethod.GET )
    @ResponseBody
    public ModelAndView userSignOut(HttpServletRequest request) {
        if(isLoggedIn(request)){
            request.getSession().setAttribute(LoginSessionEnum.LOGGED_IN.toString(),false);
            request.getSession().setAttribute(LoginSessionEnum.ID.toString(),null);
            request.getSession().setAttribute(LoginSessionEnum.EMAIL.toString(),null);
            request.getSession().setAttribute(AdminProfileEnum.UNIQUE_ID.toString(),null);
            request.getSession().setAttribute(AdminProfileEnum.DESCRIPTION.toString(),null);
            request.getSession().setAttribute(AdminProfileEnum.COURSE_ROLE.toString(),null);
            request.getSession().setAttribute(AdminProfileEnum.SUBJECT_ROLE.toString(),null);
            request.getSession().setAttribute(AdminProfileEnum.FIRST_NAME.toString(),null);
            request.getSession().setAttribute(LoginSessionEnum.TYPE_OF_USER.toString(),null);
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/addCourseAction", method = RequestMethod.POST)
    public ModelAndView addCourseAction (HttpServletRequest request,AddCourseForm f) {
        if(isLoggedIn(request)){
            Course c = new Course();
            c.setAdminID((long)request.getSession().getAttribute(LoginSessionEnum.ID.toString()));
            c.setCourseName(f.getCourseName());
            String[] subjects = f.getSubjectNames().split(",");

            boolean response = adminService.addCourse(c,subjects);
            if(!response){
                return new ModelAndView("redirect:/addCourse?responseErrorMessage=CourseWithSameNameExists");
            }
            return new ModelAndView("redirect:/addCourse");
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/addSubjectAction", method = RequestMethod.POST)
    public ModelAndView addSubjectAction (HttpServletRequest request,AddSubjectNameForm f) {
        if(isLoggedIn(request)){
            SubjectName s = new SubjectName();
            s.setAdminId((long) request.getSession().getAttribute(LoginSessionEnum.ID.toString()));
            s.setSubjectName(f.getSubjectName());
            adminService.addSubjectName(s);
            return new ModelAndView("redirect:/addSubject");
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/addSubject", method = RequestMethod.GET)
    public ModelAndView addSubject(HttpServletRequest request) {
        if(isLoggedIn(request)){
            ModelAndView mv = new ModelAndView("site/add_subject");
            List<SubjectName> configuredSubjectNames = adminService.getSubjectNames((long) request.getSession().getAttribute(LoginSessionEnum.ID.toString()));
            mv.addObject("existingSubjects",configuredSubjectNames);
            String subjectPermission = getSubjectRoleForAdminFromSession(request);
            mv.addObject("subject", new AddSubjectNameForm());
            mv.addObject("subjectPermission",subjectPermission);
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/deleteSubject", method = RequestMethod.GET)
    public ModelAndView deleteSubject(HttpServletRequest request) {
        if(isLoggedIn(request) && isPermittedToDeleteSubject(request)){
            ModelAndView mv = new ModelAndView("redirect:/addSubject");
            adminService.deleteSubjectForAdmin(Long.parseLong(request.getParameter("id")),getAdminIDFromSession(request));
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value="/editSubjectName", method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String editSubjectName(HttpServletRequest request, @RequestBody EditSubjectName editSubjectName) {
        if(isLoggedIn(request)){
            if(isPermittedToEditSubject(request)){
                if(null != editSubjectName && StringUtils.isNotBlank(editSubjectName.getSubjectId())&& StringUtils.isNotBlank(editSubjectName.getSubjectName())){
                    long subjectNameId = Long.parseLong(editSubjectName.getSubjectId());
                    adminService.updateSubjectNameForAdmin(subjectNameId,getAdminIDFromSession(request),editSubjectName.getSubjectName());
                    return editSubjectName.getSubjectName();
                }else {
                    return "INVALID_REQUEST";
                }
            } else {
                return "Admin not authorized to Edit Subjects";
            }
        } else {
            return "Authorization_Error";
        }
    }

    @RequestMapping(value = "/addCourse", method = RequestMethod.GET)
    public ModelAndView addCourse(HttpServletRequest request) {
        if(isLoggedIn(request)){
            List<String> formSubjectNames = new ArrayList<>();
            String message = "";
            String action = "addCourseAction";
            List<SubjectName> configuredSubjectNames = adminService.getSubjectNames((long)request.getSession().getAttribute(LoginSessionEnum.ID.toString()));
            if(null != configuredSubjectNames && configuredSubjectNames.size()>0){
                for(SubjectName subjectName: configuredSubjectNames){
                    formSubjectNames.add(subjectName.getSubjectName());
                }
            } else {
                message = "There are no subjects configured for adding them to Course. Please add Subjects before adding any course. <a href='/addSubject'>Add Subject</a>";
                formSubjectNames.add("No Subject To Select");
                action = "#";
            }
            ModelAndView mv = new ModelAndView("site/add_course");
            mv.addObject("course", new AddCourseForm());
            mv.addObject("subjects", formSubjectNames);
            mv.addObject("message", message);
            mv.addObject("action", action);
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/viewPendingRequests", method = RequestMethod.GET)
    public ModelAndView viewPendingRequests(HttpServletRequest request) {
        if(isLoggedIn(request)){
            ModelAndView mv = new ModelAndView("site/pending_requests");
            long adminId = getAdminIDFromSession(request);
            List<SubscriptionDetailView> subscriptionDetails = adminService.getPendingSubscriptionRequest(adminId);
            mv.addObject("requests",subscriptionDetails);
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/pendingRequestAction", method = RequestMethod.GET)
    public ModelAndView pendingRequestAction(HttpServletRequest request) {
        if(isLoggedIn(request)){
            long adminId = getAdminIDFromSession(request);
            ModelAndView mv = new ModelAndView("site/pending_requests");
            if(null != request.getParameter("action") && null != request.getParameter("userid")){
                String action = request.getParameter("action");
                long userId = Long.parseLong(request.getParameter("userid"));
                SubscriptionDetail s = new SubscriptionDetail();
                s.setAdminId(adminId);
                s.setUserId(userId);
                if(SubscriptionStatus.APPROVED.toString().equalsIgnoreCase(action)){
                    adminService.approveSubscriptionRequest(s);
                } else if(SubscriptionStatus.DECLINED.toString().equalsIgnoreCase(action)){
                    adminService.declineSubscriptionRequest(s);
                }
            }
            List<SubscriptionDetailView> subscriptionDetails = adminService.getPendingSubscriptionRequest(adminId);
            mv.addObject("requests",subscriptionDetails);
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }


    private boolean isLoggedIn(HttpServletRequest request){
        String typeOfUser =  (String)request.getSession().getAttribute(LoginSessionEnum.TYPE_OF_USER.toString());
        boolean isAdmin = StringUtils.equalsIgnoreCase("admin",typeOfUser);
        return (request.getSession().getAttribute(LoginSessionEnum.LOGGED_IN.toString())==true) && isAdmin;
    }

    private Long getAdminIDFromSession(HttpServletRequest request){
        Object o = request.getSession().getAttribute(LoginSessionEnum.ID.toString());
        if(null != o){
            return (long) o;
        }
        return null;
    }
    private String getSubjectRoleForAdminFromSession(HttpServletRequest request){
        Object o = request.getSession().getAttribute(AdminProfileEnum.SUBJECT_ROLE.toString());
        if(null != o){
            return (String) o;
        }
        return null;
    }
    private boolean isPermittedToDeleteSubject(HttpServletRequest request){
        String subjectRole = getSubjectRoleForAdminFromSession(request);
        return StringUtils.equalsIgnoreCase(AdminPermission.DELETE.toString(),subjectRole);
    }
    private boolean isPermittedToEditSubject(HttpServletRequest request){
        String subjectRole = getSubjectRoleForAdminFromSession(request);
        return StringUtils.equalsIgnoreCase(AdminPermission.UPDATE.toString(),subjectRole);
    }
}
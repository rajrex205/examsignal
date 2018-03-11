package com.springapp.mvc.controller;

import com.springapp.mvc.model.AddExamForm;
import com.springapp.mvc.model.AddQuestionForm;
import com.springapp.mvc.model.UpdateExamDurationForm;
import com.springapp.mvc.model.UpdateQuestionForm;
import com.springapp.orm.hibernate.model.*;
import com.springapp.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExamConfController {
    final private static String ADMIN_DASHBOARD = "admindashboard";
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/addExam", method = RequestMethod.GET)
    public ModelAndView addExam(HttpServletRequest request) {
        if(isLoggedIn(request)){
            List<String> formCourses = new ArrayList<>();
            List<Course> configuredCourseByAdmin = adminService.getCourses((long)request.getSession().getAttribute(LoginSessionEnum.ID.toString()));
            if(null != configuredCourseByAdmin && configuredCourseByAdmin.size()>0){
                for(Course c: configuredCourseByAdmin){
                    formCourses.add(c.getCourseName());
                }
            } else {
                formCourses.add("");
            }
            ModelAndView mv = new ModelAndView("site/add_exam");
            mv.addObject("exam", new AddExamForm());
            mv.addObject("courses", formCourses);
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/addExamAction", method = RequestMethod.POST)
    public ModelAndView addExamAction (HttpServletRequest request,AddExamForm f) {
        if(isLoggedIn(request)){
            Course c = new Course();
            c.setCourseName(f.getCourseName());
            c.setAdminID((long) request.getSession().getAttribute(LoginSessionEnum.ID.toString()));
            long courseID = adminService.getCourseIdForCourseNameAndAdminId(c);
            Exam exam = new Exam();
            exam.setCourseID(courseID);
            exam.setExamName(f.getExamName());
            exam.setExamType(f.getExamType());
            exam.setDuration(f.getDuration());
            boolean isExamAdded = adminService.addExam(exam);
            if(isExamAdded){
                long examId = adminService.fetchExamIdByExamName(exam);
                return new ModelAndView("redirect:/addQuestion?examid="+examId);
            } else{
                return new ModelAndView("redirect:/"+ADMIN_DASHBOARD);
            }
        } else {
            return new ModelAndView("redirect:/");
        }
    }
    @RequestMapping(value = "/viewAllExams", method = RequestMethod.GET)
    public ModelAndView viewAllExams(HttpServletRequest request) {
        long adminId = getAdminIDFromSession(request);
        List<AllExams> allExams = adminService.getExamsForAdmin(adminId);
        ModelAndView mv = new ModelAndView("site/view_admin_exams");
        mv.addObject("exams",allExams);
        return  mv;
    }

    @RequestMapping(value = "/addQuestion", method = RequestMethod.GET)
    public ModelAndView addQuestion(HttpServletRequest request) {
        if(isLoggedIn(request)){
            ModelAndView mv = new ModelAndView("site/add_question");
            if(null != request.getParameter("examid") && String.valueOf(request.getParameter("examid")).length()>0){
                long examid = Long.parseLong((String) request.getParameter("examid"));
                List<Subject> subjects = adminService.getSubjectsForExam(examid);
                AddQuestionForm f = new AddQuestionForm();
                f.setExamId(examid);
                mv.addObject("question", f);
                mv.addObject("subjects", subjects);
                mv.addObject("examid", examid);

                long selectedSubjectId = 0;
                f.setSubjectId(0);
                String selectedSubjectName = "Please select Subject to add the questions.";
                if(null != request.getParameter("selectedSubjectId") && String.valueOf(request.getParameter("selectedSubjectId")).length()>0){
                    selectedSubjectId = Long.parseLong((String) request.getParameter("selectedSubjectId"));
                    f.setSubjectId(selectedSubjectId);
                    selectedSubjectName = adminService.getSubjectName(selectedSubjectId);
                }

                mv.addObject("selectedSubjectName", selectedSubjectName);
                return mv;
            } else {
                return new ModelAndView("redirect:/");
            }
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/addQuestionAction", method = RequestMethod.POST)
    public ModelAndView addQuestionAction (HttpServletRequest request,AddQuestionForm f) {
        if(isLoggedIn(request)){
            Question q = new Question();
            q.setExamId(f.getExamId());
            q.setSubjectId(f.getSubjectId());
            q.setQuestionText(f.getQuestion());
            q.setMarks(f.getMarks());
            q.setOption1(f.getOption1());
            q.setOption2(f.getOption2());
            q.setOption3(f.getOption3());
            q.setOption4(f.getOption4());
            q.setAnswer(f.getAnswer());
            adminService.addQuestion(q);
            return new ModelAndView("redirect:/addQuestion?examid="+f.getExamId()+"&selectedSubjectId="+f.getSubjectId());
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/manageExam", method = RequestMethod.GET)
    public ModelAndView manageExam(HttpServletRequest request) {
        if(isLoggedIn(request)){
            long adminId = getAdminIDFromSession(request);
            if(null != request.getParameter("examId") && String.valueOf(request.getParameter("examId")).length()>0){
                long examId = Long.parseLong(request.getParameter("examId"));
                if(adminService.isExamUnderAdmin(examId,adminId)){
                    ModelAndView mv = new ModelAndView("site/manage_exam");
                    Exam e = adminService.getExamFromId(examId);
                    mv.addObject("examDescription",e.getExamName());
                    mv.addObject("examid",examId);

                    UpdateExamDurationForm f = new UpdateExamDurationForm();
                    f.setExamId(examId);
                    f.setDuration(String.valueOf(e.getDuration()));
                    mv.addObject("updateDuration",f);

                    List<Question> questions = adminService.getQuestionsForExam(examId);
                    mv.addObject("questions",questions);
                    return mv;
                }
            }
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/updateDuration", method = RequestMethod.POST)
    public ModelAndView updateDurationAction (HttpServletRequest request,UpdateExamDurationForm f) {
        if(isLoggedIn(request)){
            adminService.updateExamDuration(f.getExamId(),Integer.parseInt(f.getDuration()));
            return new ModelAndView("redirect:/manageExam?examId="+f.getExamId());
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/editQuestion", method = RequestMethod.GET)
    public ModelAndView editQuestion(HttpServletRequest request) {
        if(isLoggedIn(request)){
            long adminId = getAdminIDFromSession(request);
            if(null != request.getParameter("questionid") && String.valueOf(request.getParameter("questionid")).length()>0) {
                long questionId = Long.parseLong(request.getParameter("questionid"));
                long examId = Long.parseLong(request.getParameter("examid"));
                if(adminService.isQuestionUnderAdmin(questionId,adminId)){
                    UpdateQuestionForm f = new UpdateQuestionForm();
                    Question q = adminService.getQuestionFromId(questionId);
                    f.setQuestionId(questionId);
                    f.setExamId(examId);
                    f.setQuestion(q.getQuestionText());
                    f.setOption1(q.getOption1());
                    f.setOption2(q.getOption2());
                    f.setOption3(q.getOption3());
                    f.setOption4(q.getOption4());
                    f.setMarks(q.getMarks());
                    f.setAnswer(q.getAnswer());
                    ModelAndView mv = new ModelAndView("site/edit_question");
                    mv.addObject("editq",f);
                    return mv;
                }
            }

        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/updateQuestionAction", method = RequestMethod.POST)
    public ModelAndView updateQuestionAction (HttpServletRequest request,UpdateQuestionForm f) {
        if(isLoggedIn(request)){
            Question q = new Question();
            q.setId(f.getQuestionId());
            q.setQuestionText(f.getQuestion());
            q.setMarks(f.getMarks());
            q.setOption1(f.getOption1());
            q.setOption2(f.getOption2());
            q.setOption3(f.getOption3());
            q.setOption4(f.getOption4());
            q.setAnswer(f.getAnswer());
            adminService.updateQuestion(q);
            return new ModelAndView("redirect:/manageExam?examId="+f.getExamId());
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/deleteQuestion", method = RequestMethod.GET)
    public ModelAndView deleteQuestion(HttpServletRequest request) {
        if(isLoggedIn(request)){
            long adminId = getAdminIDFromSession(request);
            if(null != request.getParameter("questionid") && String.valueOf(request.getParameter("questionid")).length()>0) {
                long questionId = Long.parseLong(request.getParameter("questionid"));
                long examId = Long.parseLong(request.getParameter("examid"));
                if(adminService.isQuestionUnderAdmin(questionId,adminId)){
                    adminService.deleteQuestion(questionId);
                    ModelAndView mv = new ModelAndView("redirect:/manageExam?examId="+examId);
                    return mv;
                }
            }

        }
        return new ModelAndView("redirect:/");
    }

    private boolean isLoggedIn(HttpServletRequest request){
        String typeOfUser =  (String)request.getSession().getAttribute(LoginSessionEnum.TYPE_OF_USER.toString());
        boolean isAdmin = StringUtils.equalsIgnoreCase("admin", typeOfUser);
        return (request.getSession().getAttribute(LoginSessionEnum.LOGGED_IN.toString())==true) && isAdmin;
    }

    private Long getAdminIDFromSession(HttpServletRequest request){
        Object o = request.getSession().getAttribute(LoginSessionEnum.ID.toString());
        if(null != o){
            return (long) o;
        }
        return null;
    }
}
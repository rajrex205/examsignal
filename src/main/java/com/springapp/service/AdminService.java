package com.springapp.service;

import com.springapp.mvc.model.SubscriptionDetailView;
import com.springapp.orm.hibernate.model.*;
import com.springapp.repository.*;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class AdminService {
    private CourseDaoImpl courseDao;
    private SubjectDaoImpl subjectDao;
    private SubjectNameDaoImpl subjectNameDao;
    private ExamDaoImpl examDao;
    private QuestionDaoImpl questionDao;
    private SubscriptionDetailDaoImpl subscriptionDetailDao;
    private PreferredCourseDaoImpl preferredCourseDao;

    public void setPreferredCourseDao(PreferredCourseDaoImpl preferredCourseDao) {
        this.preferredCourseDao = preferredCourseDao;
    }

    public void setSubscriptionDetailDao(SubscriptionDetailDaoImpl subscriptionDetailDao) {
        this.subscriptionDetailDao = subscriptionDetailDao;
    }

    public void setQuestionDao(QuestionDaoImpl questionDao) {
        this.questionDao = questionDao;
    }

    public void setCourseDao(CourseDaoImpl courseDao) {
        this.courseDao = courseDao;
    }

    public void setSubjectDao(SubjectDaoImpl subjectDao) {
        this.subjectDao = subjectDao;
    }

    public void setSubjectNameDao(SubjectNameDaoImpl subjectNameDao) {
        this.subjectNameDao = subjectNameDao;
    }

    public void setExamDao(ExamDaoImpl examDao) {
        this.examDao = examDao;
    }

    public boolean addCourse(Course course, String[] subjects ){
        if(isCourseWithSameNameExists(course.getAdminID(), course.getCourseName())){
            return false;
        } else {
            courseDao.insertCourse(course);
            long courseID = courseDao.fetchCourseID(course);
            for(String subjectName: subjects){
                Subject s = new Subject();
                s.setCourseID(courseID);
                s.setSubjectName(subjectName);
                subjectDao.insertSubject(s);
            }
            return true;
        }
    }
    public boolean addSubjectName(SubjectName subjectName){
        Long existingSubjectNameId = subjectNameDao.fetchSubjectID(subjectName);
        if(null == existingSubjectNameId){
            subjectNameDao.insertSubjectName(subjectName);
            return true;
        } else{
            return false;
        }
    }

    public boolean addExam(Exam exam){
        Long existingExamWithSameName = examDao.fetchExamID(exam);
        if(null == existingExamWithSameName){
            examDao.insertExam(exam);
            return true;
        } else {
            return false;
        }
    }

    public boolean addQuestion(Question q){
        questionDao.insertQuestion(q);;
        return true;
    }

    public boolean updateQuestion(Question q){
        questionDao.updateQuestionForID(q);;
        return true;
    }

    public boolean updateExamDuration(long examId, int duration){
        examDao.updateDuration(examId,duration);
        return true;
    }

    public Long fetchExamIdByExamName(Exam exam){
        return examDao.fetchExamID(exam);
    }

    public List<SubjectName> getSubjectNames(long adminID){
        return subjectNameDao.fetchActiveSubjectNamesForAdmin(adminID);
    }

    public boolean deleteSubjectForAdmin(long subjectNameId, long adminId){
        SubjectName sn = subjectNameDao.fetchSubjectNameFromID(subjectNameId);
        subjectNameDao.deleteSubjectName(subjectNameId);
        List<Course> courses =  courseDao.fetchActiveCoursesForAdmin(adminId);
        if(null != courses){
            for(Course c : courses){
                Subject s = new Subject();
                s.setCourseID(c.getId());
                s.setSubjectName(sn.getSubjectName());
                Long subjectId = subjectDao.fetchSubjectID(s);
                if(null != subjectId){
                    subjectDao.deleteSubject(subjectId);
                    questionDao.deleteAllQuestionsForSubject(subjectId);
                }
            }
        }
        return true;
    }

    public boolean updateSubjectNameForAdmin(long subjectNameId, long adminId, String newNameForSubject){
        SubjectName sn = subjectNameDao.fetchSubjectNameFromID(subjectNameId);
        subjectNameDao.updateSubjectName(subjectNameId,newNameForSubject);
        List<Course> courses =  courseDao.fetchActiveCoursesForAdmin(adminId);
        if(null != courses){
            for(Course c : courses){
                Subject s = new Subject();
                s.setCourseID(c.getId());
                s.setSubjectName(sn.getSubjectName());
                Long subjectId = subjectDao.fetchSubjectID(s);
                if(null != subjectId){
                    subjectDao.updateSubjectName(subjectId, newNameForSubject);
                }
            }
        }
        return true;
    }

    public String getSubjectName(long subjectId){
        Subject s = subjectDao.fetchSubjectFromID(subjectId);
        return s != null ? s.getSubjectName() : "";
    }

    public List<Subject> getSubjectsForExam(long examid){
        return subjectDao.fetchSubjectsForExam(examid);
    }

    public List<Course> getCourses(long adminId){
        return courseDao.fetchActiveCoursesForAdmin(adminId);
    }

    public long getCourseIdForCourseNameAndAdminId(Course course){
        return courseDao.fetchCourseID(course);
    }

    public List<AllExams> getExamsForAdmin(long adminId){
        return examDao.fetchAllExamsOfAdmin(adminId);
    }

    public List<Question> getQuestionsForExam(long examId){
        return questionDao.fetchAllQuestionForExam(examId);
    }

    public boolean isExamUnderAdmin(long examId, long adminId){
        int c = examDao.examIdCountForAdminId(examId,adminId);
        return c == 1;
    }

    public boolean isQuestionUnderAdmin(long questionId, long adminId){
        int c = questionDao.questionIdCountForAdminId(questionId,adminId);
        return c == 1;
    }

    public Exam getExamFromId(long examId){
        return examDao.fetchExamFromID(examId);
    }

    public Question getQuestionFromId(long questionId){
        return questionDao.fetchQuestionFromID(questionId);
    }

    public boolean deleteQuestion(long questionId){
        questionDao.deleteQuestion(questionId);
        return true;
    }

    public boolean approveSubscriptionRequest(SubscriptionDetail subscriptionDetail){
        subscriptionDetail.setStatus(SubscriptionStatus.APPROVED.toString());
        subscriptionDetailDao.updateSubscriptDetail(subscriptionDetail);
        List<Course> courses = courseDao.fetchActiveCoursesForAdmin(subscriptionDetail.getAdminId());
        if(null != courses && courses.size()>0){
            for(Course c: courses){
                PreferredCourse preferredCourse = new PreferredCourse();
                preferredCourse.setUserId(subscriptionDetail.getUserId());
                preferredCourse.setCourseId(c.getId());
                preferredCourseDao.insertPreferredCourse(preferredCourse);
            }
        }
        return true;
    }

    public boolean declineSubscriptionRequest(SubscriptionDetail subscriptionDetail){
        subscriptionDetail.setStatus(SubscriptionStatus.DECLINED.toString());
        subscriptionDetailDao.updateSubscriptDetail(subscriptionDetail);
        return true;
    }

    public List<SubscriptionDetailView> getPendingSubscriptionRequest(long adminId){
        List<SubscriptionDetailView> subscriptionDetails = subscriptionDetailDao.fetchSubscriptDetailForAdmin(adminId);
        ListIterator<SubscriptionDetailView> itr = subscriptionDetails.listIterator();
        while(itr.hasNext()){
            SubscriptionDetailView s = itr.next();
            if(!SubscriptionStatus.REQUESTED.toString().equalsIgnoreCase(s.getStatus())){
                itr.remove();
            }
        }
        return subscriptionDetails;
    }

    private boolean isCourseWithSameNameExists(long adminId, String courseName){
        int count = courseDao.fetchCourseCountWithGivenNameForAdmin(adminId, courseName);
        return count > 0;
    }
}

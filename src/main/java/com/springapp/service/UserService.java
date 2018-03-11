package com.springapp.service;

import com.springapp.mvc.model.*;
import com.springapp.orm.hibernate.model.*;
import com.springapp.repository.*;
import com.springapp.util.PointsCalculator;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;


public class UserService {
    private UserProfileDaoImpl userProfileDao;
    private SubscriptionDetailDaoImpl subscriptionDetailDao;
    private AdminProfileDaoImpl adminProfileDao;
    private CourseDaoImpl courseDao;
    private PreferredCourseDaoImpl preferredCourseDao;
    private ExamDaoImpl examDao;
    private SubjectDaoImpl subjectDao;
    private QuestionDaoImpl questionDao;
    private UserExamDaoImpl userExamDao;
    private PointsDaoImpl pointsDao;
    private OfferDaoImpl offerDao;

    public void setOfferDao(OfferDaoImpl offerDao) {
        this.offerDao = offerDao;
    }

    public void setPointsDao(PointsDaoImpl pointsDao) {
        this.pointsDao = pointsDao;
    }

    public void setUserExamDao(UserExamDaoImpl userExamDao) {
        this.userExamDao = userExamDao;
    }

    public void setQuestionDao(QuestionDaoImpl questionDao) {
        this.questionDao = questionDao;
    }

    public void setSubjectDao(SubjectDaoImpl subjectDao) {
        this.subjectDao = subjectDao;
    }

    public void setExamDao(ExamDaoImpl examDao) {
        this.examDao = examDao;
    }

    public void setPreferredCourseDao(PreferredCourseDaoImpl preferredCourseDao) {
        this.preferredCourseDao = preferredCourseDao;
    }

    public void setCourseDao(CourseDaoImpl courseDao) {
        this.courseDao = courseDao;
    }

    public void setAdminProfileDao(AdminProfileDaoImpl adminProfileDao) {
        this.adminProfileDao = adminProfileDao;
    }

    public void setSubscriptionDetailDao(SubscriptionDetailDaoImpl subscriptionDetailDao) {
        this.subscriptionDetailDao = subscriptionDetailDao;
    }

    public void setUserProfileDao(UserProfileDaoImpl userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    public ViewUserProfile fetchUserProfile(long userId){
        return userProfileDao.fetchUserProfileForView(userId);
    }

    public boolean updateProfile(EditUserProfileForm form){
        userProfileDao.updateProfile(form);
        return true;
    }

    public String fetchSubscriptionStatus(long userId, long groupId){
        return subscriptionDetailDao.fetchRequestStatus(groupId,userId);
    }

    public Long fetchGroupID(String uniqueGroupId){
        return adminProfileDao.fetchIdFromUniqueID(uniqueGroupId);
    }

    public void createSubscriptionRequest(long userId, long groupId){
        SubscriptionDetail subscriptionDetail = new SubscriptionDetail();
        subscriptionDetail.setAdminId(groupId);
        subscriptionDetail.setUserId(userId);
        subscriptionDetail.setStatus(SubscriptionStatus.REQUESTED.toString());
        subscriptionDetailDao.insertSubscriptionDetail(subscriptionDetail);
    }

    public List<ViewSubscribedGroup> fetchActiveGroupsForUser(long userId){
        return subscriptionDetailDao.fetchActiveSubscriptDetailForUser(userId);
    }

    public boolean deleteGroupForUser(long userId, long groupId){
        SubscriptionDetail s = new SubscriptionDetail();
        s.setUserId(userId);
        s.setAdminId(groupId);
        s.setStatus(SubscriptionStatus.DELETED.toString());
        subscriptionDetailDao.updateSubscriptDetail(s);
        List<Course> courses = courseDao.fetchActiveCoursesForAdmin(s.getAdminId());
        if(null != courses && courses.size()>0){
            for(Course c: courses){
                PreferredCourse preferredCourse = new PreferredCourse();
                preferredCourse.setUserId(s.getUserId());
                preferredCourse.setCourseId(c.getId());
                preferredCourseDao.deletePreferredCourse(preferredCourse);
            }
        }

        return true;
    }

    public boolean unsubscribeCourseForUser(long userId, long courseId){
        PreferredCourse p = new PreferredCourse();
        p.setUserId(userId);
        p.setCourseId(courseId);
        preferredCourseDao.deletePreferredCourse(p);
        return true;
    }

    public List<Course> fetchAddableCoursesForUser(long userId){
        return courseDao.fetchAddableCourses(userId);
    }
    public boolean addMoreCoursesToUser(long userId, String courses){
        String[] courseArray = courses.split(",");
        for(String c: courseArray){
            PreferredCourse p = new PreferredCourse();
            p.setUserId(userId);
            p.setCourseId(Long.parseLong(c));
            preferredCourseDao.insertPreferredCourse(p);
        }
        return true;
    }

    public List<ViewPreferredCourse> fetchAllActivePreferredCoursesForUser(long userId){
        return preferredCourseDao.fetchAllActivePreferredCoursesForUser(userId);
    }

    public List<SelectExamView> fetchAllExamsForUser(long userId){
        return examDao.fetchAllExamsOfUserBasedOnPreferredCourses(userId);
    }

    public ExamDetailsPopup getExamDetails(Long examId){
        return examDao.getExamDetails(examId);
    }

    public List<Subject> getSubjectsForExam(long examid){
        return subjectDao.fetchSubjectsForExam(examid);
    }

    public Integer getQuestionCountForSubject(long subjectId, long examId){
        return questionDao.getQuestionCountForSubject(subjectId,examId);
    }

    public Question getFirstQuestionOfExamForSubject(long subjectId, long examId){
        return questionDao.getFirstQuestionOfExamForSubject(subjectId,examId);
    }

    public long setUpForStartExam(long exam_id, long user_id, Date startTime){
        return userExamDao.initExamSetup(exam_id,user_id,startTime);
    }
    public Question getQuestion(long questionId){
        return questionDao.fetchQuestionFromID(questionId);
    }
    public boolean updateScore(long scoreId, long questionId, int score){
        return userExamDao.updateScore(scoreId,questionId,score);
    }
    public Exam fetchExam(long examId){
        return examDao.fetchExamFromID(examId);
    }

    public Question getNextQuestion(int currentQuestionNumberInSession, long examId, long subjectId){
        return questionDao.fetchQuestion(++currentQuestionNumberInSession,examId,subjectId);
    }

    public Question getPreviousQuestion(int currentQuestionNumberInSession, long examId, long subjectId){
        return questionDao.fetchQuestion(--currentQuestionNumberInSession,examId,subjectId);
    }

    public Question getQuestion(int questionNumber, long examId, long subjectId){
        return questionDao.fetchQuestion(questionNumber,examId,subjectId);
    }
    public boolean saveMarksAndPoints(long scoreId, Points points){
        int maxMarks = questionDao.getTotalMarksForExam(scoreId);
        int totalMarks = userExamDao.getTotalMarksScored(scoreId);
        userExamDao.saveExamResult(scoreId,maxMarks,totalMarks);
        int pointsSecured = PointsCalculator.getPointsForMarks(totalMarks,maxMarks);
        points.setPoints(pointsSecured);
        pointsDao.insertPoints(points);
        return true;
    }

    public UserExam getResultPopup(long scoreId){
        return userExamDao.getFinalExamResult(scoreId);
    }

    public FBSharePopupDetail getFBSharePopup(long scoreId){
        return userExamDao.getFBPopUpDetails(scoreId);
    }

    public List<ExamResultView> getUserExamResult(long userId){
        return userExamDao.getUserExamResults(userId);
    }
    public String fetchOffer(){
        List<Offer> list = offerDao.fetchOffer();
        return (null != list && list.size() > 0 && StringUtils.isNotBlank(list.get(0).getDescription())) ?
                list.get(0).getDescription() : "";
    }



}

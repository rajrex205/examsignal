package com.springapp.repository;

import com.springapp.mvc.model.ExamResultView;
import com.springapp.mvc.model.FBSharePopupDetail;
import com.springapp.orm.hibernate.model.Points;
import com.springapp.orm.hibernate.model.UserExam;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class UserExamDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //returns score_id
    public long initExamSetup(long exam_id, long user_id, Date startTime){
        String sql = "insert into user_exam " +
                "(exam_id, user_id, start_time) " +
                "values (?, ?, ?)";

        jdbcTemplate.update(sql, new Object[]{exam_id,user_id,startTime
        });

        sql = "select score_id from user_exam where exam_id = ? and user_id = ? and is_deleted= 0 order by created_ts desc limit 1";
        Long scoreId =  this.jdbcTemplate.queryForObject(sql, new Object[]{exam_id,user_id}, Long.class);

        sql = "insert into user_score " +
                "(score_id,q_id,points_scored) " +
                "select ?,id,0 from question where exam_id = ? and is_deleted = 0";

        jdbcTemplate.update(sql, new Object[]{scoreId,exam_id
        });
        return  scoreId;
    }

    public boolean updateScore(long scoreId, long questionId, int score){
        String sql = "update user_score " +
                "set points_scored = ? " +
                "where score_id = ? and q_id = ?";

        jdbcTemplate.update(sql, new Object[]{score,scoreId,questionId
        });
        return true;
    }

    public int getTotalMarksScored(final long scoreId){
        String query = "select sum(points_scored) from user_score " +
                "where score_id = ?";
        List<Integer> count = this.jdbcTemplate.query(query, new Object[]{scoreId}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNumber) throws SQLException {
                return  rs.getInt(1);
            }});
        if(count != null && count.size()>0){
            return count.get(0);
        } else {
            return 0;
        }
    }

    public void saveExamResult(long scoreId, int maxMarks, int totalMarks){
        String sql = "update user_exam " +
                "set max_marks = ? , total_marks = ? , end_time = NOW() " +
                "where score_id = ? and is_deleted = 0";

        jdbcTemplate.update(sql, new Object[]{maxMarks,totalMarks,scoreId
        });
    }

    public List<ExamResultView> getUserExamResults(long userId){
        String query = "select ue.start_time,e.exam_name,c.id,c.course_name,ue.max_marks,ue.total_marks from user_exam ue " +
                "inner join exam e on e.id = ue.exam_id and ue.is_deleted = 0 inner join course c on c.id = e.course_id " +
                "where ue.user_id = ? ";
        return this.jdbcTemplate.query(query, new Object[]{userId}, new RowMapper<ExamResultView>() {
            @Override
            public ExamResultView mapRow(ResultSet rs, int rowNumber) throws SQLException {
                ExamResultView e = new ExamResultView();
                e.setExamDate(rs.getDate(1));
                e.setExamName(rs.getString(2));
                e.setCourseId(rs.getLong(3));
                e.setCourseName(rs.getString(4));
                e.setMaxMarks(String.valueOf(rs.getInt(5)));
                e.setTotalMarks(String.valueOf(rs.getInt(6)));
                return e;
            }});
    }

    public UserExam getFinalExamResult(long scoreId){
        String query = "select u.start_time, u.end_time, u.max_marks, u.total_marks, e.duration from user_exam u inner join exam e on u.exam_id = e.id " +
                "where score_id = ? and u.is_deleted = 0 and e.is_deleted = 0";
        List<UserExam> examResult = this.jdbcTemplate.query(query, new Object[]{scoreId}, new RowMapper<UserExam>() {
            @Override
            public UserExam mapRow(ResultSet rs, int rowNumber) throws SQLException {
                UserExam e = new UserExam();
                e.setStartTime(rs.getTimestamp(1));
                e.setEndTime(rs.getTimestamp(2));
                e.setMaxMarks(rs.getInt(3));
                e.setTotalMarks(rs.getInt(4));
                e.setExamDuration(rs.getInt(5));
                return e;
            }});
        if(null != examResult && examResult.size()>0){
            return examResult.get(0);
        }
        return null;
    }
    public FBSharePopupDetail getFBPopUpDetails(long scoreId){
        String query = "select f.first_name, c.course_name, e.exam_name, u.max_marks, u.total_marks,u.user_id from user_exam u " +
                "inner join exam e on u.exam_id = e.id inner join course c on c.id = e. course_id " +
                "inner join user_profile f on f.id= u.user_id " +
                "where score_id = ? and u.is_deleted = 0 and e.is_deleted = 0";
        List<FBSharePopupDetail> fbSharePopupDetails = this.jdbcTemplate.query(query, new Object[]{scoreId}, new RowMapper<FBSharePopupDetail>() {
            @Override
            public FBSharePopupDetail mapRow(ResultSet rs, int rowNumber) throws SQLException {
                FBSharePopupDetail e = new FBSharePopupDetail();
                e.setUserName(rs.getString(1));
                e.setCourseName(rs.getString(2));
                e.setExamName(rs.getString(3));
                e.setTotalMarks(String.valueOf(rs.getInt(4)));
                e.setMarksSecured(String.valueOf(rs.getInt(5)));
                e.setUserId(rs.getLong(6));
                return e;
            }});
        if(null != fbSharePopupDetails && fbSharePopupDetails.size()>0){
            //Enrich user points
            String userPointsQuery = "select sum(p.points) from points p " +
                    "where p.is_deleted=0 and p.user_id = ?";
            List<Integer> userTotalPoints = this.jdbcTemplate.query(userPointsQuery, new Object[]{fbSharePopupDetails.get(0).getUserId()}, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNumber) throws SQLException {
                    return rs.getInt(1);
                }
            });
            Integer userPoints = 0;
            if(null!=userTotalPoints && userTotalPoints.size()>0){
                userPoints = userTotalPoints.get(0);
            }
            fbSharePopupDetails.get(0).setTotalPoints(String.valueOf(userPoints));
            return fbSharePopupDetails.get(0);
        }
        return null;
    }
}
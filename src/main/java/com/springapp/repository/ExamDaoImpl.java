package com.springapp.repository;

import com.springapp.mvc.model.ExamDetailsPopup;
import com.springapp.mvc.model.SelectExamView;
import com.springapp.orm.hibernate.model.AllExams;
import com.springapp.orm.hibernate.model.Exam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertExam(Exam exam){
        String sql = "insert into exam " +
                "(course_id, exam_name,exam_type,duration) " +
                "values (?, ?,?, ?)";

        jdbcTemplate.update(sql, new Object[]{exam.getCourseID(),exam.getExamName(), exam.getExamType(), exam.getDuration()
        });
    }
    public void deleteExam(Exam exam){
        String sql = "update exam set is_deleted = 1" +
                "where id = ? ";

        jdbcTemplate.update(sql, new Object[]{exam.getId()});
    }
    public void updateDuration(long examId, int duration){
        String sql = "update exam set duration= ?" +
                "where id = ? ";

        jdbcTemplate.update(sql, new Object[]{duration,examId});
    }
    public Long fetchExamID(final Exam exam){
        String query = "select id , course_id, exam_name, duration from exam " +
                "where course_id = ? and exam_name = ? and is_deleted = 0";
        List<Exam> examList  = this.jdbcTemplate.query(query, new Object[]{exam.getCourseID(), exam.getExamName()}, new RowMapper<Exam>() {
            @Override
            public Exam mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Exam exam1 = new Exam();
                exam1.setId(rs.getLong(1));
                exam1.setCourseID(rs.getLong(2));
                exam1.setExamName(rs.getString(3));
                exam1.setDuration(rs.getInt(4));
                return  exam1;
            }});
        if(null != examList && examList.size()==1){
            return examList.get(0).getId();
        }
        return  null;
    }
    public Exam fetchExamFromID(final long examID){
        String query = "select id , course_id, exam_name, duration from exam " +
                "where id = ? and is_deleted = 0";
        List<Exam> examList  = this.jdbcTemplate.query(query, new Object[]{examID}, new RowMapper<Exam>() {
            @Override
            public Exam mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Exam exam1 = new Exam();
                exam1.setId(rs.getLong(1));
                exam1.setCourseID(rs.getLong(2));
                exam1.setExamName(rs.getString(3));
                exam1.setDuration(Integer.parseInt(rs.getString(4)));
                return  exam1;
            }});
        if(null != examList && examList.size()==1){
            return examList.get(0);
        }
        return  null;
    }

    public List<AllExams> fetchAllExamsOfAdmin(final long adminId){
        String query = "select c.id,e.id,c.course_name,e.exam_name from course c inner join exam e on e.course_id = c.id " +
                "where c.admin_id = ? and e.is_deleted = 0 order by c.course_name,e.exam_name";
        return this.jdbcTemplate.query(query, new Object[]{adminId}, new RowMapper<AllExams>() {
            @Override
            public AllExams mapRow(ResultSet rs, int rowNumber) throws SQLException {
                AllExams exam1 = new AllExams();
                exam1.setAdminId(adminId);
                exam1.setCourseId(rs.getLong(1));
                exam1.setExamId(rs.getLong(2));
                exam1.setCourseName(rs.getString(3));
                exam1.setExamName(rs.getString(4));
                return  exam1;
            }});
    }

    public List<SelectExamView> fetchAllExamsOfUserBasedOnPreferredCourses(final long userId){
        String query = "select c.id, c.course_name, e.id, e.exam_name, e.exam_type from preferred_course p inner join exam e on e.course_id = p.course_id " +
                "inner join course c on c.id=p.course_id where p.user_id = ? and p.is_deleted = 0 and e.is_deleted = 0 and c.is_deleted=0 order by e.exam_name";
        List<SelectExamView> list =  this.jdbcTemplate.query(query, new Object[]{userId}, new RowMapper<SelectExamView>() {
            @Override
            public SelectExamView mapRow(ResultSet rs, int rowNumber) throws SQLException {
                SelectExamView e = new SelectExamView();
                e.setCourseId(rs.getLong(1));
                e.setCourseName(rs.getString(2));
                e.setExamId(rs.getLong(3));
                e.setExamName(rs.getString(4));
                e.setTypeOfExam(rs.getString(5));
                e.setIsExamAttempted("N"); /// TBD
                return  e;
            }});

        if(null != list){
            for(SelectExamView s: list){
                String examAttemptQuery = "select count(*) from user_exam where exam_id = ? and user_id = ? and is_deleted = 0";
                int count = this.jdbcTemplate.queryForObject(examAttemptQuery,new Object[]{s.getExamId(),userId},Integer.class);
                s.setIsExamAttempted(count>0 ? "Y":"N");
            }
            return list;
        }else{
            return null;
        }

    }

    public ExamDetailsPopup getExamDetails(Long examId){
        String query = "select e.id, c.course_name,e.exam_name,s.subject_name,temp.total,e.duration \n" +
                "from course c inner join exam e on e.course_id=c.id \n" +
                "inner join subject s on s.course_id=e.course_id \n" +
                "inner join (select q.subject_id as subjectid,sum(q.marks) as total from question q where q.exam_id=? and q.is_deleted=0\n" +
                "group by q.subject_id) temp on temp.subjectid = s.id\n" +
                "where e.id=? and c.is_deleted=0 and e.is_deleted=0 and s.is_deleted=0;";
        List<ExamDetailsPopup> list =  this.jdbcTemplate.query(query, new Object[]{examId, examId}, new RowMapper<ExamDetailsPopup>() {
            @Override
            public ExamDetailsPopup mapRow(ResultSet rs, int rowNumber) throws SQLException {
                ExamDetailsPopup e = new ExamDetailsPopup();
                e.setExamId(rs.getLong(1));
                e.setCourseName(rs.getString(2));
                e.setExamName(rs.getString(3));
                e.setAllSubjects(rs.getString(4));
                e.setTotalMarks(rs.getLong(5));
                e.setDuration(rs.getLong(6));
                return  e;
            }});
        if(null != list && list.size()>0){
            ExamDetailsPopup result = new ExamDetailsPopup();
            ExamDetailsPopup row1 = list.get(0);
            result.setExamId(row1.getExamId());
            result.setCourseName(row1.getCourseName());
            result.setExamName(row1.getExamName());
            result.setDuration(row1.getDuration());
            Map<String, Long> subjectDetails = new HashMap<>();
            List<String> subjectList = new ArrayList<>();
            Long totalMarks = 0L;
            for(ExamDetailsPopup e: list){
                subjectDetails.put(e.getAllSubjects(),e.getTotalMarks());
                subjectList.add(e.getAllSubjects());
                totalMarks+=e.getTotalMarks();
            }
            result.setTotalMarks(totalMarks);
            result.setAllSubjects(StringUtils.join(subjectList,", "));
            result.setSubjectAndMarks(subjectDetails);
            return result;
        }
        return null;
    }

    public int examIdCountForAdminId(final long examId, final long adminId){
        String query = "select count(1) from course c inner join exam e on e.course_id = c.id " +
                "where c.admin_id = ? and e.id = ? and e.is_deleted = 0";
        List<Integer> count = this.jdbcTemplate.query(query, new Object[]{adminId,examId}, new RowMapper<Integer>() {
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
}
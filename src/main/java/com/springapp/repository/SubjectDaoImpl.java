package com.springapp.repository;

import com.springapp.orm.hibernate.model.Subject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SubjectDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertSubject(Subject subject){
        String sql = "insert into subject " +
                "(course_id, subject_name) " +
                "values (?, ?)";

        jdbcTemplate.update(sql, new Object[]{subject.getCourseID(),subject.getSubjectName()
        });
    }
    public void deleteSubject(long subjectId){
        String sql = "update subject set is_deleted=1 " +
                "where id = ? ";

        jdbcTemplate.update(sql, new Object[]{subjectId});
    }
    public void updateSubjectName(long subjectId, String newNameForSubject){
        String sql = "update subject set subject_name=? " +
                "where id = ? ";

        jdbcTemplate.update(sql, new Object[]{newNameForSubject, subjectId});
    }
    public Long fetchSubjectID(final Subject subject){
        String query = "select id , course_id, subject_name from subject " +
                "where course_id = ? and subject_name = ? and is_deleted = 0";
        List<Subject> subjectList  = this.jdbcTemplate.query(query, new Object[]{subject.getCourseID(), subject.getSubjectName()}, new RowMapper<Subject>() {
            @Override
            public Subject mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Subject subject1 = new Subject();
                subject1.setId(rs.getLong(1));
                subject1.setCourseID(rs.getLong(2));
                subject1.setSubjectName(rs.getString(3));
                return  subject1;
            }});
        if(null != subjectList && subjectList.size()==1){
            return subjectList.get(0).getId();
        }
        return  null;
    }

    public Subject fetchSubjectFromID(final long subjectID){
        String query = "select id , course_id, subject_name from subject " +
                "where id = ? and is_deleted = 0";
        List<Subject> subjectList  = this.jdbcTemplate.query(query, new Object[]{subjectID}, new RowMapper<Subject>() {
            @Override
            public Subject mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Subject subject1 = new Subject();
                subject1.setId(rs.getLong(1));
                subject1.setCourseID(rs.getLong(2));
                subject1.setSubjectName(rs.getString(3));
                return  subject1;
            }});
        if(null != subjectList && subjectList.size()==1){
            return subjectList.get(0);
        }
        return  null;
    }

    public List<Subject> fetchSubjectsForExam(final long examID){
        String query = "select s.id , s.course_id, s.subject_name from exam e " +
                "inner join subject s on s.course_id = e.course_id where e.id = ? and e.is_deleted = 0 order by s.subject_name";
        return this.jdbcTemplate.query(query, new Object[]{examID}, new RowMapper<Subject>() {
            @Override
            public Subject mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Subject subject1 = new Subject();
                subject1.setId(rs.getLong(1));
                subject1.setCourseID(rs.getLong(2));
                subject1.setSubjectName(rs.getString(3));
                return  subject1;
            }});
    }
}
package com.springapp.repository;

import com.springapp.orm.hibernate.model.SubjectName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SubjectNameDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertSubjectName(SubjectName subjectName){
        String sql = "insert into subject_name " +
                "(admin_id, subject_name) " +
                "values (?, ?)";

        jdbcTemplate.update(sql, new Object[]{subjectName.getAdminId(), subjectName.getSubjectName()
        });
    }
    public void deleteSubjectName(long subjectNameId){
        String sql = "update subject_name set is_deleted=1 " +
                "where id = ?";

        jdbcTemplate.update(sql, new Object[]{subjectNameId});
    }

    public void updateSubjectName(long subjectNameId, String newNameForSubject){
        String sql = "update subject_name set subject_name=? " +
                "where id = ?";

        jdbcTemplate.update(sql, new Object[]{newNameForSubject, subjectNameId});
    }

    public Long fetchSubjectID(final SubjectName subjectName){
        String query = "select id , admin_id, subject_name from subject_name " +
                "where admin_id = ? and subject_name = ? ";
        List<SubjectName> subjectNameList  = this.jdbcTemplate.query(query, new Object[]{subjectName.getAdminId(), subjectName.getSubjectName()}, new RowMapper<SubjectName>() {
            @Override
            public SubjectName mapRow(ResultSet rs, int rowNumber) throws SQLException {
                SubjectName subjectName1 = new SubjectName();
                subjectName1.setId(rs.getLong(1));
                subjectName1.setAdminId(rs.getLong(2));
                subjectName1.setSubjectName(rs.getString(3));
                return  subjectName1;
            }});
        if(null != subjectNameList && subjectNameList.size()==1){
            return subjectNameList.get(0).getId();
        }
        return  null;
    }
    public SubjectName fetchSubjectNameFromID(final long subjectNameID){
        String query = "select id , admin_id, subject_name from subject_name " +
                "where id = ?";
        List<SubjectName> subjectNameList  = this.jdbcTemplate.query(query, new Object[]{subjectNameID}, new RowMapper<SubjectName>() {
            @Override
            public SubjectName mapRow(ResultSet rs, int rowNumber) throws SQLException {
                SubjectName subjectName1 = new SubjectName();
                subjectName1.setId(rs.getLong(1));
                subjectName1.setAdminId(rs.getLong(2));
                subjectName1.setSubjectName(rs.getString(3));
                return  subjectName1;
            }});
        if(null != subjectNameList && subjectNameList.size()==1){
            return subjectNameList.get(0);
        }
        return  null;
    }
    public List<SubjectName> fetchActiveSubjectNamesForAdmin(final long adminId){
        String query = "select id , admin_id, subject_name from subject_name " +
                "where admin_id = ? and is_deleted = 0";
        return this.jdbcTemplate.query(query, new Object[]{adminId}, new RowMapper<SubjectName>() {
            @Override
            public SubjectName mapRow(ResultSet rs, int rowNumber) throws SQLException {
                SubjectName subjectName1 = new SubjectName();
                subjectName1.setId(rs.getLong(1));
                subjectName1.setAdminId(rs.getLong(2));
                subjectName1.setSubjectName(rs.getString(3));
                return  subjectName1;
            }});
    }
}
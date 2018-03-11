package com.springapp.repository;

import com.springapp.orm.hibernate.model.AdminProfile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class AdminProfileDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AdminProfile fetchProfileFromID(final long adminId){
        String query = "select id, unique_id, description, course_role,subject_role,is_deleted,create_ts from admin_profile " +
                "where id = ?";
        List<AdminProfile> adminProfileList  = this.jdbcTemplate.query(query, new Object[]{adminId}, new RowMapper<AdminProfile>() {
            @Override
            public AdminProfile mapRow(ResultSet rs, int rowNumber) throws SQLException {
                AdminProfile adminProfile1 = new AdminProfile();
                adminProfile1.setId(rs.getLong(1));
                adminProfile1.setUniqueId(rs.getString(2));
                adminProfile1.setDescription(rs.getString(3));
                adminProfile1.setCourseRole(rs.getString(4));
                adminProfile1.setSubjectRole(rs.getString(5));
                adminProfile1.setIsDeleted(rs.getInt(6));
                adminProfile1.setCreateTS(rs.getDate(7));
                return  adminProfile1;
            }});
        if(null != adminProfileList && adminProfileList.size()==1){
            return adminProfileList.get(0);
        }
        return  null;
    }

    public Long fetchIdFromUniqueID(String adminUniqueId){
        String query = "select id from admin_profile " +
                "where upper(unique_id) = ? and is_deleted = 0";
        List<Long> adminProfileList  = this.jdbcTemplate.query(query, new Object[]{adminUniqueId.toUpperCase()}, new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNumber) throws SQLException {
                return rs.getLong(1);
            }});
        if(null != adminProfileList && adminProfileList.size()==1){
            return adminProfileList.get(0);
        }
        return  null;
    }
}
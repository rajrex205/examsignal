package com.springapp.repository;

import com.springapp.orm.hibernate.model.FacebookAccess;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FacebookAccessDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(String fbId, String emailId){
        String sql = "insert into facebook_access " +
                "(fb_id, email) " +
                "values (?, ?)";

        jdbcTemplate.update(sql, new Object[]{fbId,emailId
        });
    }

    public FacebookAccess fetchOnFbId(String fbId){
        String query = "select id , fb_id, email, is_deleted, created_ts from facebook_access " +
                "where fb_id = ? and is_deleted = 0";
        List<FacebookAccess> facebookAccessList = this.jdbcTemplate.query(query, new Object[]{fbId}, new RowMapper<FacebookAccess>() {
            @Override
            public FacebookAccess mapRow(ResultSet rs, int rowNumber) throws SQLException {
                FacebookAccess facebookAccess = new FacebookAccess();
                facebookAccess.setId(rs.getLong(1));
                facebookAccess.setFbId(rs.getString(2));
                facebookAccess.setEmail(rs.getString(3));
                facebookAccess.setIsDeleted(rs.getInt(4));
                facebookAccess.setCreatedTS(rs.getTimestamp(5));
                return  facebookAccess;
            }});
        if(null != facebookAccessList && facebookAccessList.size()==1){
            return facebookAccessList.get(0);
        }
        return  null;
    }

    public void updateFacebookAccessForEmailId(String oldTempEmailId, String newEmailId){
        String sql = "update facebook_access " +
                "set email = ? " +
                "where email = ? and is_deleted=0 ";

        jdbcTemplate.update(sql, new Object[]{newEmailId,oldTempEmailId
        });
    }
}
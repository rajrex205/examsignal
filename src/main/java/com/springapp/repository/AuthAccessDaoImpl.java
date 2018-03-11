package com.springapp.repository;

import com.springapp.orm.hibernate.model.AuthAccess;
import com.springapp.request.RegistrationRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AuthAccessDaoImpl implements AuthenticationDao{
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AuthAccess getUser(String emailId, String authMode){
        String query = "select id, email, password, auth_mode, type_of_user, is_deleted, created_ts from auth_access " +
                "where email = ? and auth_mode = ? and is_deleted = 0";
        List<AuthAccess> authAccessList  = this.jdbcTemplate.query(query, new Object[]{emailId, authMode}, new RowMapper<AuthAccess>() {
            @Override
            public AuthAccess mapRow(ResultSet rs, int rowNumber) throws SQLException {
                AuthAccess authAccess = new AuthAccess();
                authAccess.setId(rs.getLong(1));
                authAccess.setEmail(rs.getString(2));
                authAccess.setPassword(rs.getString(3));
                authAccess.setAuthMode(rs.getString(4));
                authAccess.setTypeOfUser(rs.getString(5));
                authAccess.setIsDeleted(rs.getInt(6));
                authAccess.setCreatedTS(rs.getTimestamp(7));
                return  authAccess;
            }});
        if(null != authAccessList && authAccessList.size()==1){
            return authAccessList.get(0);
        }
        return  null;
    }

    public void insertAuthAccess(RegistrationRequest request) {
        String sql = "insert into auth_access " +
                "(email, password, auth_mode, type_of_user) values (?, ?, ?, 'user')";

        jdbcTemplate.update(sql, new Object[]{request.getEmail(), request.getEncryptedPassword(), request.getAuthMode()
        });
    }

    public void updateAuthAccessForEmailId(String oldTempEmailId, String newEmailId){
        String sql = "update auth_access " +
                "set email = ? " +
                "where email = ? and is_deleted=0 and type_of_user='user'";

        jdbcTemplate.update(sql, new Object[]{newEmailId,oldTempEmailId
        });
    }

    public void resetPassword(String password, String emailId){
        String sql = "update auth_access " +
                "set password = ? " +
                "where email = ? and is_deleted=0";

        jdbcTemplate.update(sql, new Object[]{password, emailId
        });
    }

}
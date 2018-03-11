package com.springapp.repository;

import com.springapp.orm.hibernate.model.ResetCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ResetCodeDaoImpl {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setCode(ResetCode resetCode){
        if(exists(resetCode.getEmail())){
            updateCode(resetCode);
        } else {
            insertCode(resetCode);
        }
    }

    public void insertCode(ResetCode resetCode){

        String sql = "insert into reset_code " +
                "(email, code) " +
                "values (?, ?)";

        jdbcTemplate.update(sql, new Object[]{resetCode.getEmail(), resetCode.getCode()
        });
    }

    public void updateCode(ResetCode resetCode){

        String sql = "update reset_code " +
                "set code = ? " +
                "where email = ?";

        jdbcTemplate.update(sql, new Object[]{resetCode.getCode(), resetCode.getEmail()
        });
    }

    public boolean codeMatches(ResetCode resetCode){
        String query = "select email from reset_code " +
                "where email = ? and code = ?";
        List<String> existingEmail = this.jdbcTemplate.query(query, new Object[]{resetCode.getEmail(), resetCode.getCode()}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString(1);
            }
        });
        return  null != existingEmail && existingEmail.size()>0;
    }

    public boolean exists(String email){
        String query = "select email from reset_code " +
                "where email = ?";
        List<String> existingEmail = this.jdbcTemplate.query(query, new Object[]{email}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString(1);
            }
        });
        return  null != existingEmail && existingEmail.size()>0;
    }
}
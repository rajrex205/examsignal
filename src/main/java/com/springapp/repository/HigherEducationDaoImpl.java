package com.springapp.repository;

import com.springapp.orm.hibernate.model.HigherEducation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HigherEducationDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<HigherEducation> fetchHigherEducation(){
        String query = "select id , education from higher_education ";
        return this.jdbcTemplate.query(query, new Object[]{}, new RowMapper<HigherEducation>() {
            @Override
            public HigherEducation mapRow(ResultSet rs, int rowNumber) throws SQLException {
                HigherEducation higherEducation = new HigherEducation();
                higherEducation.setId(rs.getLong(1));
                higherEducation.setEducation(rs.getString(2));
                return  higherEducation;
            }});
    }
}
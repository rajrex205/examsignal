package com.springapp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import com.springapp.mvc.model.ViewTestimonial;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TestimonialDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ViewTestimonial> fetchTestimonials(){
        String query = "select name, testimonial, user_point, photoid from testimonial " +
                "where is_deleted = 0 order by insert_date desc limit 5";
        return this.jdbcTemplate.query(query, new Object[]{}, new RowMapper<ViewTestimonial>() {
            @Override
            public ViewTestimonial mapRow(ResultSet rs, int rowNumber) throws SQLException {
                ViewTestimonial viewTestimonial= new ViewTestimonial();
                viewTestimonial.setUserName(rs.getString(1));
                viewTestimonial.setTestimonial(rs.getString(2));
                viewTestimonial.setPoints(rs.getString(3));
                viewTestimonial.setIconFileName(rs.getString(4));
                return  viewTestimonial;
            }});
    }
}
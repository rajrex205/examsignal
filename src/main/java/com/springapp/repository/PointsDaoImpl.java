package com.springapp.repository;

import com.springapp.orm.hibernate.model.Points;
import org.springframework.jdbc.core.JdbcTemplate;

public class PointsDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertPoints(Points points){
        String sql = "insert into points " +
                "(user_id, points, reason_code,reason_description) " +
                "values (?, ?, ?, ?)";

        jdbcTemplate.update(sql, new Object[]{points.getUserId(),points.getPoints(),points.getReasonCode(),points.getReasonDescription()
        });
    }
}
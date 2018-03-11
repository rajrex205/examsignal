package com.springapp.repository;

import com.springapp.orm.hibernate.model.Offer;
import com.springapp.orm.hibernate.model.Points;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OfferDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Offer> fetchOffer(){
        String query = "select id , description from offer ";
        return this.jdbcTemplate.query(query, new Object[]{}, new RowMapper<Offer>() {
            @Override
            public Offer mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Offer offer = new Offer();
                offer.setId(rs.getLong(1));
                offer.setDescription(rs.getString(2));
                return offer;
            }});
    }
}
package com.springapp.repository;

import com.springapp.mvc.model.ViewPreferredCourse;
import com.springapp.orm.hibernate.model.PreferredCourse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PreferredCourseDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertPreferredCourse(PreferredCourse preferredCourse){
        String sql = "insert into preferred_course " +
                "(user_id, course_id) " +
                "values (?, ?)";

        jdbcTemplate.update(sql, new Object[]{preferredCourse.getUserId(),preferredCourse.getCourseId()
        });
    }

    public void deletePreferredCourse(PreferredCourse preferredCourse){
        String sql = "update preferred_course set is_deleted=1 " +
                "where user_id = ? and course_id = ? and is_deleted=0";

        jdbcTemplate.update(sql, new Object[]{preferredCourse.getUserId(),preferredCourse.getCourseId()});
    }
    public List<ViewPreferredCourse> fetchAllActivePreferredCoursesForUser(final long userId){
        String query = "select c.id,c.course_name,a.unique_id from preferred_course p " +
                "inner join course c on c.id = p.course_id inner join admin_profile a on c.admin_id = a.id " +
                "where p.user_id = ? and p.is_deleted = 0 and a.is_deleted =0 and c.is_deleted = 0 order by a.unique_id";
        return this.jdbcTemplate.query(query, new Object[]{userId}, new RowMapper<ViewPreferredCourse>() {
            @Override
            public ViewPreferredCourse mapRow(ResultSet rs, int rowNumber) throws SQLException {
                ViewPreferredCourse viewPreferredCourse = new ViewPreferredCourse();
                viewPreferredCourse.setCourseId(rs.getLong(1));
                viewPreferredCourse.setCourseName(rs.getString(2));
                viewPreferredCourse.setGroupName(rs.getString(3));
                return viewPreferredCourse;
            }});
    }
}
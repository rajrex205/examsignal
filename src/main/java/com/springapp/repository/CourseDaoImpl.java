package com.springapp.repository;


import com.springapp.mvc.model.ViewPreferredCourse;
import com.springapp.orm.hibernate.model.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CourseDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertCourse(Course course){
        String sql = "insert into course " +
                "(admin_id, course_name) " +
                "values (?, ?)";

        jdbcTemplate.update(sql, new Object[]{course.getAdminID(),course.getCourseName()
        });
    }
    public void deleteCourse(Course course){
        String sql = "update course set is_deleted = 1" +
                "where id = ? ";

        jdbcTemplate.update(sql, new Object[]{course.getId()});
    }
    public Long fetchCourseID(final Course course){
        String query = "select id , admin_id, course_name from course " +
                "where admin_id = ? and course_name = ? and is_deleted = 0";
        List<Course> courseList  = this.jdbcTemplate.query(query, new Object[]{course.getAdminID(), course.getCourseName()}, new RowMapper<Course>() {
            @Override
            public Course mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Course course1 = new Course();
                course1.setId(rs.getLong(1));
                course1.setAdminID(rs.getLong(2));
                course1.setCourseName(rs.getString(3));
                return  course1;
            }});
        if(null != courseList && courseList.size()==1){
            return courseList.get(0).getId();
        }
        return  null;
    }
    public int fetchCourseCountWithGivenNameForAdmin(long adminId, String courseName){
        String query = "select count(id) from course " +
                "where admin_id = ? and course_name = ? and is_deleted = 0";
        return this.jdbcTemplate.queryForObject(query, new Object[]{adminId, courseName}, Integer.class);
    }
    public List<Course> fetchActiveCoursesForAdmin(final long adminId){
        String query = "select id , admin_id, course_name from course " +
                "where is_deleted = 0 and admin_id = ?";
        return  this.jdbcTemplate.query(query, new Object[]{adminId}, new RowMapper<Course>() {
            @Override
            public Course mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Course course1 = new Course();
                course1.setId(rs.getLong(1));
                course1.setAdminID(rs.getLong(2));
                course1.setCourseName(rs.getString(3));
                return  course1;
            }});
    }

    public List<ViewPreferredCourse> fetchPublicGroupCourses(){
        String query = "select c.id , c.course_name, unique_id from course c " +
                "inner join admin_profile p on c.admin_id = p.id where c.is_deleted = 0 and p.is_public = 1 and p.is_deleted = 0";
        return this.jdbcTemplate.query(query, new Object[]{}, new RowMapper<ViewPreferredCourse>() {
            @Override
            public ViewPreferredCourse mapRow(ResultSet rs, int rowNumber) throws SQLException {
                ViewPreferredCourse viewPreferredCourse = new ViewPreferredCourse();
                viewPreferredCourse.setCourseId(rs.getLong(1));
                viewPreferredCourse.setCourseName(rs.getString(2));
                viewPreferredCourse.setGroupName(rs.getString(3));
                return viewPreferredCourse;
            }});
    }

    public List<Course> fetchAddableCourses(long userId){
        String query = "select c.id,c.course_name from admin_profile a inner join course c on c.admin_id=a.id " +
                "where a.is_public=1 and c.id not in (select p.course_id from preferred_course p where p.user_id = ? and p.is_deleted=0) " +
                "UNION " +
                "select c.id,c.course_name from subscription_detail s inner join course c on s.admin_id=c.admin_id " +
                "where s.user_id=? and c.id not in (select p.course_id from preferred_course p where p.user_id = ? and p.is_deleted=0)";

        return this.jdbcTemplate.query(query, new Object[]{userId,userId,userId}, new RowMapper<Course>() {
            @Override
            public Course mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Course c = new Course();
                c.setId(rs.getLong(1));
                c.setCourseName(rs.getString(2));
                return c;
            }});
    }
}
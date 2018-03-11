package com.springapp.repository;

import com.springapp.mvc.model.EditUserProfileForm;
import com.springapp.mvc.model.ViewUserProfile;
import com.springapp.orm.hibernate.model.UserProfile;
import com.springapp.request.RegistrationRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserProfileDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertUsrProfile(RegistrationRequest request){
        String sql = "insert into user_profile " +
                "(id, user_id, first_name, last_name,gender,phone,address,highest_education,referral_code) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ? )";

        jdbcTemplate.update(sql, new Object[]{request.getId(),request.getUserID(), request.getFirstName(), request.getLastName(),
                request.getGender(), request.getPhone(), request.getAddress(), request.getHighestEducation(), request.getReferralCode()
        });

        if(null != request.getPreferredCourse() && request.getPreferredCourse().size()>0){
            for(Long courseId: request.getPreferredCourse()){
                String preferredCourseSQL = "insert into preferred_course " +
                        "(user_id, course_id) " +
                        "values (?, ?)";

                jdbcTemplate.update(preferredCourseSQL, new Object[]{request.getId(),courseId
                });
            }
        }
    }

    public UserProfile fetchProfileFromID(final long userId){
        String query = "select id, user_id, first_name, last_name from user_profile " +
                "where id = ? and is_deleted = 0";
        List<UserProfile> userProfileList  = this.jdbcTemplate.query(query, new Object[]{userId}, new RowMapper<UserProfile>() {
            @Override
            public UserProfile mapRow(ResultSet rs, int rowNumber) throws SQLException {
                UserProfile userProfile1 = new UserProfile();
                userProfile1.setId(rs.getLong(1));
                userProfile1.setUserId(rs.getString(2));
                userProfile1.setFirstName(rs.getString(3));
                userProfile1.setLastName(rs.getString(4));
                return  userProfile1;
            }});
        if(null != userProfileList && userProfileList.size()==1){
            return userProfileList.get(0);
        }
        return  null;
    }

    public Long fetchIdFromUniqueId(final String uniqueId){
        String query = "select id from user_profile " +
                "where user_id = ? and is_deleted = 0";
        List<Long> userProfileList  = this.jdbcTemplate.query(query, new Object[]{uniqueId}, new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNumber) throws SQLException {
                return  rs.getLong(1);
            }});
        if(null != userProfileList && userProfileList.size()==1){
            return userProfileList.get(0);
        }
        return  null;
    }

    public ViewUserProfile fetchUserProfileForView(long userId){
        String query = "select a.id, a.email, p.user_id, p.first_name, p.last_name, p.gender, p.phone, p.address, p.highest_education from user_profile p " +
                "inner join auth_access a on a.id=p.id and p.is_deleted=0 and a.is_deleted=0 where a.id = ? and a.type_of_user='user'";
        List<ViewUserProfile> userProfileList  = this.jdbcTemplate.query(query, new Object[]{userId}, new RowMapper<ViewUserProfile>() {
            @Override
            public ViewUserProfile mapRow(ResultSet rs, int rowNumber) throws SQLException {
                ViewUserProfile viewUserProfile = new ViewUserProfile();
                viewUserProfile.setId(rs.getLong(1));
                viewUserProfile.setEmail(rs.getString(2));
                viewUserProfile.setUniqueId(rs.getString(3));
                viewUserProfile.setFirstName(rs.getString(4));
                viewUserProfile.setLastName(rs.getString(5));
                viewUserProfile.setGender(rs.getString(6));
                viewUserProfile.setPhone(rs.getString(7));
                viewUserProfile.setAddress(rs.getString(8));
                viewUserProfile.setHighestEducation(rs.getString(9));
                return viewUserProfile;
            }});
        if(null != userProfileList && userProfileList.size()==1){
            ViewUserProfile viewUserProfile = userProfileList.get(0);

            //Enrich Preferred Courses
            String preferredCourseQuery = "select c.course_name from preferred_course p inner join course c on c.id = p.course_id " +
                    "and p.is_deleted=0 and c.is_deleted=0 where p.user_id = ? order by p.created_ts desc";
            List<String> preferredCourseList = this.jdbcTemplate.query(preferredCourseQuery , new Object[]{userId}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNumber) throws SQLException {
                    return rs.getString(1);
                }
            });
            String preferredCourses = "";
            if(null!=preferredCourseList && preferredCourseList.size()>0){
                preferredCourses = StringUtils.join(preferredCourseList,",");
            }
            viewUserProfile.setPreferredCourses(preferredCourses);

            //Enrich user points
            String userPointsQuery = "select sum(p.points) from points p " +
                    "where p.is_deleted=0 and p.user_id = ?";
            List<Integer> userTotalPoints = this.jdbcTemplate.query(userPointsQuery, new Object[]{userId}, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNumber) throws SQLException {
                    return rs.getInt(1);
                }
            });
            Integer userPoints = 0;
            if(null!=userTotalPoints && userTotalPoints.size()>0){
                userPoints = userTotalPoints.get(0);
            }
            viewUserProfile.setPoints(userPoints.toString());

            return viewUserProfile;
        }
        return  null;
    }

    public void updateProfile(EditUserProfileForm form){
        String sql = "update user_profile " +
                "set first_name=?, last_name=?,gender=?,phone=?,address=?,highest_education=? " +
                "where id = ?";

        jdbcTemplate.update(sql, new Object[]{form.getFirstName(), form.getLastName(), form.getGender(), form.getPhone(),
                form.getAddress(), form.getHighestEducation(), form.getId()
        });

        String expirePreferredCourses = "update preferred_course " +
                "set is_deleted=1 " +
                "where user_id = ? and is_deleted=0";

        jdbcTemplate.update(expirePreferredCourses, new Object[]{form.getId()
        });

        if(null != form.getPreferredCoursesList() && form.getPreferredCoursesList().size()>0){
            for(Long courseId: form.getPreferredCoursesList()){
                String preferredCourseSQL = "insert into preferred_course " +
                        "(user_id, course_id) " +
                        "values (?, ?)";

                jdbcTemplate.update(preferredCourseSQL, new Object[]{form.getId(),courseId
                });
            }
        }
    }

    public void createBlankProfileForSocialSignUp(long id, String userId){
        String blankProfileSQL = "insert into user_profile " +
                "(id, user_id) " +
                "values (?, ?)";

        jdbcTemplate.update(blankProfileSQL, new Object[]{id,userId
        });
    }
}
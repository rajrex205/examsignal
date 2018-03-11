package com.springapp.repository;

import com.springapp.mvc.model.SubscriptionDetailView;
import com.springapp.mvc.model.ViewSubscribedGroup;
import com.springapp.orm.hibernate.model.SubscriptionDetail;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SubscriptionDetailDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertSubscriptionDetail(SubscriptionDetail subscriptionDetail){
        String sql = "insert into subscription_detail " +
                "(admin_id, user_id, status) " +
                "values (?, ?, ?)";

        jdbcTemplate.update(sql, new Object[]{subscriptionDetail.getAdminId(),subscriptionDetail.getUserId(),subscriptionDetail.getStatus()
        });
    }

    public List<ViewSubscribedGroup> fetchActiveSubscriptDetailForUser(final long userId){
        String query = "select s.admin_id, s.user_id, a.unique_id from subscription_detail s " +
                "inner join admin_profile a on a.id = s.admin_id where s.user_id = ? and s.status = 'APPROVED' and a.is_deleted = 0";
        return this.jdbcTemplate.query(query, new Object[]{userId}, new RowMapper<ViewSubscribedGroup>() {
            @Override
            public ViewSubscribedGroup mapRow(ResultSet rs, int rowNumber) throws SQLException {
                ViewSubscribedGroup g = new ViewSubscribedGroup();
                g.setAdminId(rs.getLong(1));
                g.setUserId(rs.getLong(2));
                g.setGroupName(rs.getString(3));
                return g;
            }});
    }

    public List<SubscriptionDetailView> fetchSubscriptDetailForAdmin(final long adminId){
        String query = "select d.admin_id, d.user_id, d.status,d.requested_date,p.first_name,p.last_name from subscription_detail d " +
                "inner join user_profile p on p.id = d.user_id where d.admin_id = ? ";
        return this.jdbcTemplate.query(query, new Object[]{adminId}, new RowMapper<SubscriptionDetailView>() {
            @Override
            public SubscriptionDetailView mapRow(ResultSet rs, int rowNumber) throws SQLException {
                SubscriptionDetailView subscriptionDetail = new SubscriptionDetailView();
                subscriptionDetail.setAdminId(rs.getLong(1));
                subscriptionDetail.setUserId(rs.getLong(2));
                subscriptionDetail.setStatus(rs.getString(3));
                subscriptionDetail.setRequestedDate(rs.getDate(4));
                subscriptionDetail.setUserFirstName(rs.getString(5));
                subscriptionDetail.setUserLastName(rs.getString(6));
                return  subscriptionDetail;
            }});
    }

    public String fetchRequestStatus(final long adminId, final long userId){
        String query = "select status from subscription_detail " +
                "where admin_id = ? and user_id = ? ";
        List<String> list =  this.jdbcTemplate.query(query, new Object[]{adminId,userId}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNumber) throws SQLException {
                return rs.getString(1);
            }});
        if(null != list && list.size()==1){
            return list.get(0);
        }
        return  null;
    }

    public void updateSubscriptDetail(SubscriptionDetail subscriptionDetail){
        String sql = "update subscription_detail set status = ?, update_date = now() " +
                "where admin_id = ? and user_id = ? and status <> 'DELETED'";

        jdbcTemplate.update(sql, new Object[]{subscriptionDetail.getStatus(),subscriptionDetail.getAdminId(),subscriptionDetail.getUserId()
        });
    }
}
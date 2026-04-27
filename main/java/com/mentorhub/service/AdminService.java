package com.mentorhub.service;

import com.mentorhub.dto.AdminOverviewDto;
import com.mentorhub.dto.AdminUserDto;
import com.mentorhub.model.UserRole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AdminService {
    private final JdbcTemplate jdbcTemplate;

    public AdminService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AdminOverviewDto getOverview() {
        Long totalUsers = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM [user]", Long.class);
        Long totalMentors = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM [mentor]", Long.class);
        Long totalMentees = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM [mentee]", Long.class);
        Long totalBookings = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM [mentee_session]", Long.class);
        BigDecimal revenue = jdbcTemplate.queryForObject("SELECT COALESCE(SUM(total_money), 0) FROM [payment] WHERE [status] = N'Paid'", BigDecimal.class);
        return new AdminOverviewDto(
                totalUsers == null ? 0 : totalUsers,
                totalMentors == null ? 0 : totalMentors,
                totalMentees == null ? 0 : totalMentees,
                totalBookings == null ? 0 : totalBookings,
                revenue == null ? BigDecimal.ZERO : revenue
        );
    }

    public List<AdminUserDto> getUsers(String status) {
        StringBuilder sql = new StringBuilder("""
                SELECT u.user_id, u.first_name, u.last_name, u.email, u.current_job, u.[status],
                       CASE WHEN m.user_id IS NOT NULL THEN 'MENTOR'
                            WHEN me.user_id IS NOT NULL THEN 'MENTEE'
                            ELSE 'MENTEE' END AS user_role
                FROM [user] u
                LEFT JOIN [mentor] m ON m.user_id = u.user_id
                LEFT JOIN [mentee] me ON me.user_id = u.user_id
                WHERE (m.user_id IS NOT NULL OR me.user_id IS NOT NULL)
                """);
        if (status != null && !status.isBlank()) {
            sql.append(" AND u.[status] = ? ");
            sql.append(" ORDER BY u.user_id DESC");
            return jdbcTemplate.query(sql.toString(), this::mapAdminUser, status);
        }
        sql.append(" ORDER BY CASE WHEN u.[status] = N'Pending' THEN 0 ELSE 1 END, u.user_id DESC");
        return jdbcTemplate.query(sql.toString(), this::mapAdminUser);
    }

    public AdminUserDto approveUser(Integer userId) {
        return updateUserStatus(userId, "Active");
    }

    public AdminUserDto updateUserStatus(Integer userId, String status) {
        String normalizedStatus = status == null || status.isBlank() ? "Active" : status.trim();
        int updated = jdbcTemplate.update("UPDATE [user] SET [status] = ? WHERE user_id = ?", normalizedStatus, userId);
        if (updated == 0) {
            throw new IllegalArgumentException("Không tìm thấy user_id = " + userId);
        }
        AdminUserDto user = jdbcTemplate.queryForObject("""
                SELECT u.user_id, u.first_name, u.last_name, u.email, u.current_job, u.[status],
                       CASE WHEN m.user_id IS NOT NULL THEN 'MENTOR'
                            WHEN me.user_id IS NOT NULL THEN 'MENTEE'
                            ELSE 'MENTEE' END AS user_role
                FROM [user] u
                LEFT JOIN [mentor] m ON m.user_id = u.user_id
                LEFT JOIN [mentee] me ON me.user_id = u.user_id
                WHERE u.user_id = ?
                """, this::mapAdminUser, userId);
        createStatusNotification(userId, user.role().name(), normalizedStatus);
        return user;
    }

    private void createStatusNotification(Integer userId, String targetRole, String status) {
        List<Integer> adminIds = jdbcTemplate.queryForList("SELECT TOP 1 admin_id FROM [admin] ORDER BY admin_id", Integer.class);
        if (adminIds.isEmpty()) return;
        String title = "Active".equalsIgnoreCase(status) ? "Tài khoản đã được duyệt" : "Trạng thái tài khoản đã thay đổi";
        String content = "Active".equalsIgnoreCase(status)
                ? "Admin đã duyệt tài khoản của bạn. Bạn có thể sử dụng đầy đủ dashboard MentorHub."
                : "Admin đã cập nhật trạng thái tài khoản của bạn thành: " + status;
        Integer notificationId = jdbcTemplate.queryForObject("""
                INSERT INTO [notification] (admin_id, target_role, title, content, sent_date, sent_time)
                OUTPUT INSERTED.notification_id
                VALUES (?, ?, ?, ?, CONVERT(date, SYSDATETIME()), CONVERT(time(0), SYSDATETIME()))
                """, Integer.class, adminIds.get(0), targetRole, title, content);
        if (notificationId != null) {
            jdbcTemplate.update("""
                    IF NOT EXISTS (SELECT 1 FROM [user_notification] WHERE user_id = ? AND notification_id = ?)
                    INSERT INTO [user_notification] (user_id, notification_id, is_read) VALUES (?, ?, 0)
                    """, userId, notificationId, userId, notificationId);
        }
    }

    private AdminUserDto mapAdminUser(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        String fullName = (rs.getString("first_name") + " " + rs.getString("last_name")).trim();
        return new AdminUserDto(
                rs.getInt("user_id"),
                fullName,
                rs.getString("email"),
                UserRole.valueOf(rs.getString("user_role")),
                rs.getString("current_job"),
                rs.getString("status")
        );
    }
}

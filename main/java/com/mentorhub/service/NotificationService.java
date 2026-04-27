package com.mentorhub.service;

import com.mentorhub.dto.NotificationDto;
import com.mentorhub.dto.NotificationRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Service
public class NotificationService {
    private final JdbcTemplate jdbcTemplate;

    public NotificationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public NotificationDto sendNotification(NotificationRequest request) {
        Integer adminId = resolveAdminId(request.adminId());
        String targetRole = normalizeTargetRole(request.targetRole());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                    INSERT INTO [notification] (admin_id, target_role, title, content, sent_date, sent_time)
                    VALUES (?, ?, ?, ?, CONVERT(date, SYSDATETIME()), CONVERT(time(0), SYSDATETIME()))
                    """, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, adminId);
            ps.setString(2, targetRole);
            ps.setString(3, request.title().trim());
            ps.setString(4, request.content().trim());
            return ps;
        }, keyHolder);

        Integer notificationId = keyHolder.getKey().intValue();
        List<Integer> userIds = findTargetUserIds(targetRole);
        for (Integer userId : userIds) {
            jdbcTemplate.update("""
                    IF NOT EXISTS (SELECT 1 FROM [user_notification] WHERE user_id = ? AND notification_id = ?)
                    INSERT INTO [user_notification] (user_id, notification_id, is_read) VALUES (?, ?, 0)
                    """, userId, notificationId, userId, notificationId);
        }
        return getNotificationById(notificationId);
    }

    @Transactional
    public List<NotificationDto> getNotificationsForUser(Integer userId) {
        String userRole = findUserRole(userId);
        jdbcTemplate.update("""
                INSERT INTO [user_notification] (user_id, notification_id, is_read)
                SELECT ?, n.notification_id, 0
                FROM [notification] n
                WHERE (n.target_role = N'ALL' OR n.target_role = ?)
                  AND NOT EXISTS (
                      SELECT 1 FROM [user_notification] un
                      WHERE un.user_id = ? AND un.notification_id = n.notification_id
                  )
                """, userId, userRole, userId);

        return jdbcTemplate.query("""
                SELECT n.notification_id, n.admin_id, CONCAT(a.first_name, N' ', a.last_name) AS sender_name,
                       n.target_role, n.title, n.content, n.sent_date, n.sent_time, un.is_read
                FROM [user_notification] un
                JOIN [notification] n ON n.notification_id = un.notification_id
                JOIN [admin] a ON a.admin_id = n.admin_id
                WHERE un.user_id = ?
                ORDER BY n.sent_date DESC, n.sent_time DESC, n.notification_id DESC
                """, this::mapNotification, userId);
    }

    public List<NotificationDto> getNotificationsForRole(String targetRole) {
        String role = normalizeTargetRole(targetRole);
        return jdbcTemplate.query("""
                SELECT n.notification_id, n.admin_id, CONCAT(a.first_name, N' ', a.last_name) AS sender_name,
                       n.target_role, n.title, n.content, n.sent_date, n.sent_time, CAST(0 AS bit) AS is_read
                FROM [notification] n
                JOIN [admin] a ON a.admin_id = n.admin_id
                WHERE n.target_role = ? OR n.target_role = N'ALL'
                ORDER BY n.sent_date DESC, n.sent_time DESC, n.notification_id DESC
                """, this::mapNotification, role);
    }

    public void markAsRead(Integer userId, Integer notificationId) {
        jdbcTemplate.update("UPDATE [user_notification] SET is_read = 1 WHERE user_id = ? AND notification_id = ?", userId, notificationId);
    }

    private NotificationDto getNotificationById(Integer notificationId) {
        return jdbcTemplate.queryForObject("""
                SELECT n.notification_id, n.admin_id, CONCAT(a.first_name, N' ', a.last_name) AS sender_name,
                       n.target_role, n.title, n.content, n.sent_date, n.sent_time, CAST(0 AS bit) AS is_read
                FROM [notification] n
                JOIN [admin] a ON a.admin_id = n.admin_id
                WHERE n.notification_id = ?
                """, this::mapNotification, notificationId);
    }

    private String findUserRole(Integer userId) {
        Integer mentor = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM [mentor] WHERE user_id = ?", Integer.class, userId);
        if (mentor != null && mentor > 0) return "MENTOR";
        Integer mentee = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM [mentee] WHERE user_id = ?", Integer.class, userId);
        if (mentee != null && mentee > 0) return "MENTEE";
        throw new IllegalArgumentException("Không tìm thấy Mentor/Mentee user_id = " + userId);
    }

    private List<Integer> findTargetUserIds(String targetRole) {
        if ("MENTOR".equals(targetRole)) {
            return jdbcTemplate.queryForList("SELECT user_id FROM [mentor]", Integer.class);
        }
        if ("MENTEE".equals(targetRole)) {
            return jdbcTemplate.queryForList("SELECT user_id FROM [mentee]", Integer.class);
        }
        return jdbcTemplate.queryForList("""
                SELECT u.user_id
                FROM [user] u
                WHERE EXISTS (SELECT 1 FROM [mentor] m WHERE m.user_id = u.user_id)
                   OR EXISTS (SELECT 1 FROM [mentee] me WHERE me.user_id = u.user_id)
                """, Integer.class);
    }

    private Integer resolveAdminId(Integer requestedAdminId) {
        if (requestedAdminId != null) {
            Integer exists = jdbcTemplate.queryForObject(
                    "SELECT COUNT(1) FROM [admin] WHERE admin_id = ?",
                    Integer.class,
                    requestedAdminId
            );
            if (exists != null && exists > 0) return requestedAdminId;
        }

        List<Integer> adminIds = jdbcTemplate.queryForList(
                "SELECT TOP 1 admin_id FROM [admin] ORDER BY admin_id",
                Integer.class
        );
        if (adminIds.isEmpty()) {
            throw new IllegalArgumentException("Chưa có admin trong database để gửi notification.");
        }
        return adminIds.get(0);
    }

    private String normalizeTargetRole(String targetRole) {
        String role = targetRole == null ? "ALL" : targetRole.trim().toUpperCase();
        if (role.isBlank()) return "ALL";
        if (!role.equals("ALL") && !role.equals("MENTEE") && !role.equals("MENTOR")) {
            throw new IllegalArgumentException("targetRole chỉ nhận ALL, MENTEE hoặc MENTOR.");
        }
        return role;
    }

    private NotificationDto mapNotification(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Time sentTime = rs.getTime("sent_time");
        LocalTime localTime = sentTime == null ? null : sentTime.toLocalTime();
        return new NotificationDto(
                rs.getInt("notification_id"),
                rs.getInt("admin_id"),
                rs.getString("sender_name"),
                rs.getString("target_role"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getDate("sent_date") == null ? null : rs.getDate("sent_date").toLocalDate(),
                localTime,
                rs.getBoolean("is_read")
        );
    }
}

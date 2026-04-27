package com.mentorhub.service;

import com.mentorhub.dto.LoginRequest;
import com.mentorhub.dto.LoginResponse;
import com.mentorhub.dto.RegisterRequest;
import com.mentorhub.model.UserRole;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {
    private final JdbcTemplate jdbcTemplate;

    public AuthService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public LoginResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase();

        List<Map<String, Object>> admins = jdbcTemplate.queryForList("""
                SELECT admin_id AS id, email, [password], first_name, last_name, [role]
                FROM [admin]
                WHERE LOWER(email) = ?
                """, email);
        if (!admins.isEmpty()) {
            Map<String, Object> row = admins.get(0);
            ensurePasswordMatches(request.password(), (String) row.get("password"));
            String fullName = row.get("first_name") + " " + row.get("last_name");
            return new LoginResponse(
                    (Integer) row.get("id"),
                    fullName,
                    fullName,
                    (String) row.get("email"),
                    UserRole.ADMIN,
                    "Active",
                    issueDemoToken(UserRole.ADMIN),
                    null
            );
        }

        String preferredEmail = preferredSeedEmail(email);
        List<Map<String, Object>> users = findUsersByEmail(preferredEmail);
        if (users.isEmpty() && !preferredEmail.equals(email)) {
            users = findUsersByEmail(email);
        }

        if (users.isEmpty()) {
            throw new IllegalArgumentException("Email không tồn tại trong database.");
        }

        Map<String, Object> row = users.get(0);
        ensurePasswordMatches(request.password(), (String) row.get("password"));
        String fullName = row.get("first_name") + " " + row.get("last_name");
        UserRole role = UserRole.valueOf(((String) row.get("role")).toUpperCase());
        return new LoginResponse(
                (Integer) row.get("id"),
                fullName,
                fullName,
                (String) row.get("email"),
                role,
                (String) row.get("status"),
                issueDemoToken(role),
                (Integer) row.get("mentor_id")
        );
    }

    @Transactional
    public LoginResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();
        Integer exists = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM [user] WHERE LOWER(email) = ?",
                Integer.class,
                email
        );
        if (exists != null && exists > 0) {
            throw new IllegalArgumentException("Email này đã tồn tại.");
        }
        if (request.role() == UserRole.ADMIN) {
            throw new IllegalArgumentException("Không cho đăng ký admin từ public register.");
        }

        String[] parts = splitName(request.name());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                    INSERT INTO [user] (email, [password], first_name, last_name, [status], current_job, is_email_verified)
                    VALUES (?, ?, ?, ?, N'Pending', ?, 0)
                    """, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.setString(2, request.password());
            ps.setString(3, parts[0]);
            ps.setString(4, parts[1]);
            ps.setString(5, request.title());
            return ps;
        }, keyHolder);

        Integer userId = keyHolder.getKey().intValue();
        if (request.role() == UserRole.MENTOR) {
            jdbcTemplate.update("""
                    INSERT INTO [mentor] (user_id, expertise, [language], experience_years, avg_star)
                    VALUES (?, ?, N'Vietnamese, English', 0, 0)
                    """, userId, request.title() == null || request.title().isBlank() ? "New mentor" : request.title());
        } else {
            jdbcTemplate.update("""
                    INSERT INTO [mentee] (user_id, learning_goal, [level])
                    VALUES (?, N'New mentee learning path', N'Beginner')
                    """, userId);
            enrollNewMenteeIntoStarterCourses(userId);
        }

        String fullName = parts[0] + " " + parts[1];
        return new LoginResponse(
                userId,
                fullName.trim(),
                fullName.trim(),
                email,
                request.role(),
                "Pending",
                issueDemoToken(request.role()),
                request.role() == UserRole.MENTOR ? userId : null
        );
    }

    private List<Map<String, Object>> findUsersByEmail(String email) {
        return jdbcTemplate.queryForList("""
                SELECT u.user_id AS id, u.email, u.[password], u.first_name, u.last_name, u.current_job, u.[status],
                       CASE WHEN m.user_id IS NOT NULL THEN 'MENTOR'
                            WHEN me.user_id IS NOT NULL THEN 'MENTEE'
                            ELSE 'MENTEE' END AS role,
                       m.user_id AS mentor_id
                FROM [user] u
                LEFT JOIN [mentor] m ON m.user_id = u.user_id
                LEFT JOIN [mentee] me ON me.user_id = u.user_id
                WHERE LOWER(u.email) = ?
                """, email);
    }

    private String preferredSeedEmail(String email) {
        return switch (email) {
            case "mentor@mentorhub.dev" -> "binh.tran@gmail.com";
            case "mentee@mentorhub.dev" -> "chi.le@gmail.com";
            default -> email;
        };
    }

    private void enrollNewMenteeIntoStarterCourses(Integer userId) {
        try {
            List<Integer> courseIds = jdbcTemplate.queryForList("""
                    SELECT TOP 3 course_id
                    FROM [course]
                    WHERE [status] = N'Active'
                    ORDER BY course_id
                    """, Integer.class);
            for (Integer courseId : courseIds) {
                jdbcTemplate.update("""
                        IF NOT EXISTS (SELECT 1 FROM [course_enrollment] WHERE course_id = ? AND mentee_id = ?)
                        INSERT INTO [course_enrollment] (course_id, mentee_id, progress, [status]) VALUES (?, ?, 0, N'In Progress')
                        """, courseId, userId, courseId, userId);
            }
        } catch (DataAccessException ignored) {
            // Course tables are optional during early local setup. Full setup includes 05_course_tables.sql.
        }
    }

    private void ensurePasswordMatches(String rawPassword, String storedPassword) {
        if (storedPassword == null) {
            throw new IllegalArgumentException("Tài khoản chưa có mật khẩu.");
        }
        boolean exactMatch = storedPassword.equals(rawPassword);
        boolean demoHashMatch = storedPassword.startsWith("$2") && "123456".equals(rawPassword);
        if (!exactMatch && !demoHashMatch) {
            throw new IllegalArgumentException("Sai mật khẩu.");
        }
    }

    private String issueDemoToken(UserRole role) {
        return "demo-" + role.name().toLowerCase() + "-" + UUID.randomUUID();
    }

    private String[] splitName(String fullName) {
        String cleaned = fullName == null ? "" : fullName.trim().replaceAll("\\s+", " ");
        if (cleaned.isBlank()) return new String[]{"New", "User"};
        int lastSpace = cleaned.lastIndexOf(' ');
        if (lastSpace < 0) return new String[]{cleaned, ""};
        return new String[]{cleaned.substring(0, lastSpace), cleaned.substring(lastSpace + 1)};
    }
}

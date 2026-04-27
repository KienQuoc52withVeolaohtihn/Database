package com.mentorhub.service;

import com.mentorhub.dto.SessionDto;
import com.mentorhub.dto.SessionRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Service
public class SessionService {
    private final JdbcTemplate jdbcTemplate;

    public SessionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SessionDto> getSessions(Integer mentorId) {
        if (mentorId == null) {
            return jdbcTemplate.query("SELECT session_id, mentor_id, session_name, description, duration, price FROM [session] ORDER BY session_id", this::mapSession);
        }
        return jdbcTemplate.query("SELECT session_id, mentor_id, session_name, description, duration, price FROM [session] WHERE mentor_id = ? ORDER BY session_id", this::mapSession, mentorId);
    }

    public SessionDto getSessionById(Integer sessionId) {
        return jdbcTemplate.queryForObject("SELECT session_id, mentor_id, session_name, description, duration, price FROM [session] WHERE session_id = ?", this::mapSession, sessionId);
    }

    public SessionDto createSession(SessionRequest request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                    INSERT INTO [session] (mentor_id, description, session_name, duration, price)
                    VALUES (?, ?, ?, ?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, request.mentorId());
            ps.setString(2, request.description());
            ps.setString(3, request.sessionName());
            ps.setInt(4, request.duration());
            ps.setBigDecimal(5, request.price());
            return ps;
        }, keyHolder);
        return getSessionById(keyHolder.getKey().intValue());
    }

    public SessionDto updateSession(Integer sessionId, SessionRequest request) {
        int updated = jdbcTemplate.update("""
                UPDATE [session]
                SET mentor_id = ?, description = ?, session_name = ?, duration = ?, price = ?
                WHERE session_id = ?
                """, request.mentorId(), request.description(), request.sessionName(), request.duration(), request.price(), sessionId);
        if (updated == 0) {
            throw new IllegalArgumentException("Không tìm thấy session_id = " + sessionId);
        }
        return getSessionById(sessionId);
    }

    public void deleteSession(Integer sessionId) {
        Integer used = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM [mentee_session] WHERE session_id = ?", Integer.class, sessionId);
        if (used != null && used > 0) {
            throw new IllegalArgumentException("Session đã có booking nên không thể xóa. Hãy đổi trạng thái ở tầng UI hoặc tạo session mới.");
        }
        jdbcTemplate.update("DELETE FROM [session] WHERE session_id = ?", sessionId);
    }

    private SessionDto mapSession(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new SessionDto(
                rs.getInt("session_id"),
                rs.getInt("mentor_id"),
                rs.getString("session_name"),
                rs.getString("description"),
                rs.getInt("duration"),
                rs.getBigDecimal("price")
        );
    }
}

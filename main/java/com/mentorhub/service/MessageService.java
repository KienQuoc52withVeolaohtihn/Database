package com.mentorhub.service;

import com.mentorhub.dto.MessageDto;
import com.mentorhub.dto.MessageRequest;
import com.mentorhub.dto.MessageThreadDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    private final JdbcTemplate jdbcTemplate;

    public MessageService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public MessageDto sendMessage(MessageRequest request) {
        String senderRole = normalizeSenderRole(request.senderRole());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                    INSERT INTO [message] (mentor_id, mentee_id, sender_role, content, sent_at)
                    VALUES (?, ?, ?, ?, SYSDATETIME())
                    """, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, request.mentorId());
            ps.setInt(2, request.menteeId());
            ps.setString(3, senderRole);
            ps.setString(4, request.content());
            return ps;
        }, keyHolder);
        return getMessageById(keyHolder.getKey().intValue());
    }

    public List<MessageDto> getMentorMessages(Integer mentorId) {
        return jdbcTemplate.query(messageSql("WHERE msg.mentor_id = ? ORDER BY msg.sent_at, msg.message_id"), this::mapMessage, mentorId);
    }

    public List<MessageDto> getMenteeMessages(Integer menteeId) {
        return jdbcTemplate.query(messageSql("WHERE msg.mentee_id = ? ORDER BY msg.sent_at, msg.message_id"), this::mapMessage, menteeId);
    }

    public List<MessageDto> getConversation(Integer mentorId, Integer menteeId) {
        return jdbcTemplate.query(messageSql("WHERE msg.mentor_id = ? AND msg.mentee_id = ? ORDER BY msg.sent_at, msg.message_id"), this::mapMessage, mentorId, menteeId);
    }

    public List<MessageThreadDto> getMentorThreads(Integer mentorId) {
        return toThreads(getMentorMessages(mentorId));
    }

    public List<MessageThreadDto> getMenteeThreads(Integer menteeId) {
        return toThreads(getMenteeMessages(menteeId));
    }

    private MessageDto getMessageById(Integer messageId) {
        return jdbcTemplate.queryForObject(messageSql("WHERE msg.message_id = ?"), this::mapMessage, messageId);
    }

    private List<MessageThreadDto> toThreads(List<MessageDto> messages) {
        Map<String, MessageThreadDto> threads = new LinkedHashMap<>();
        Map<String, Long> counts = new LinkedHashMap<>();
        for (MessageDto message : messages) {
            String key = message.mentorId() + ":" + message.menteeId();
            counts.put(key, counts.getOrDefault(key, 0L) + 1);
            threads.put(key, new MessageThreadDto(
                    message.mentorId(),
                    message.mentorName(),
                    message.menteeId(),
                    message.menteeName(),
                    message.content(),
                    message.senderRole(),
                    message.sentAt(),
                    counts.get(key)
            ));
        }
        return List.copyOf(threads.values());
    }

    private String normalizeSenderRole(String senderRole) {
        String role = senderRole == null ? "" : senderRole.trim().toUpperCase();
        if (!role.equals("MENTOR") && !role.equals("MENTEE")) {
            throw new IllegalArgumentException("senderRole chỉ nhận MENTOR hoặc MENTEE.");
        }
        return role;
    }

    private String messageSql(String whereClause) {
        return """
                SELECT msg.message_id, msg.mentor_id, msg.mentee_id, msg.sender_role, msg.content, msg.sent_at,
                       CONCAT(mentor_user.first_name, N' ', mentor_user.last_name) AS mentor_name,
                       CONCAT(mentee_user.first_name, N' ', mentee_user.last_name) AS mentee_name
                FROM [message] msg
                JOIN [user] mentor_user ON mentor_user.user_id = msg.mentor_id
                JOIN [user] mentee_user ON mentee_user.user_id = msg.mentee_id
                """ + whereClause;
    }

    private MessageDto mapMessage(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Timestamp sentAt = rs.getTimestamp("sent_at");
        LocalDateTime localSentAt = sentAt == null ? null : sentAt.toLocalDateTime();
        String senderRole = rs.getString("sender_role");
        return new MessageDto(
                rs.getInt("message_id"),
                rs.getInt("mentor_id"),
                rs.getString("mentor_name"),
                rs.getInt("mentee_id"),
                rs.getString("mentee_name"),
                senderRole == null ? null : senderRole.toUpperCase(),
                rs.getString("content"),
                localSentAt
        );
    }
}

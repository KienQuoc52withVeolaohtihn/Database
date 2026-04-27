package com.mentorhub.service;

import com.mentorhub.dto.ReviewDto;
import com.mentorhub.dto.ReviewRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {
    private final JdbcTemplate jdbcTemplate;

    public ReviewService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReviewDto> getReviewsByMentor(Integer mentorId) {
        return jdbcTemplate.query(reviewSql("WHERE r.mentor_id = ? ORDER BY r.rated_date DESC"), this::mapReview, mentorId);
    }

    @Transactional
    public ReviewDto createReview(ReviewRequest request) {
        LocalDate today = LocalDate.now();
        jdbcTemplate.update("""
                INSERT INTO [rate] (mentor_id, mentee_id, comment, star, rated_date)
                VALUES (?, ?, ?, ?, ?)
                """, request.mentorId(), request.menteeId(), request.comment(), request.star(), java.sql.Date.valueOf(today));
        refreshMentorAverage(request.mentorId());
        return jdbcTemplate.queryForObject(reviewSql("WHERE r.mentor_id = ? AND r.mentee_id = ? AND r.rated_date = ?"),
                this::mapReview, request.mentorId(), request.menteeId(), java.sql.Date.valueOf(today));
    }

    private void refreshMentorAverage(Integer mentorId) {
        jdbcTemplate.update("""
                UPDATE [mentor]
                SET avg_star = COALESCE((SELECT CAST(AVG(CAST(star AS DECIMAL(4,2))) AS DECIMAL(3,2)) FROM [rate] WHERE mentor_id = ?), 0)
                WHERE user_id = ?
                """, mentorId, mentorId);
    }

    private String reviewSql(String whereClause) {
        return """
                SELECT r.mentor_id, r.mentee_id, CONCAT(u.first_name, N' ', u.last_name) AS mentee_name,
                       r.comment, r.star, r.rated_date
                FROM [rate] r
                JOIN [user] u ON u.user_id = r.mentee_id
                """ + whereClause;
    }

    private ReviewDto mapReview(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new ReviewDto(
                rs.getInt("mentor_id"),
                rs.getInt("mentee_id"),
                rs.getString("mentee_name"),
                rs.getString("comment"),
                rs.getInt("star"),
                rs.getDate("rated_date") == null ? null : rs.getDate("rated_date").toLocalDate()
        );
    }
}

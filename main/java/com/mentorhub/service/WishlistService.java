package com.mentorhub.service;

import com.mentorhub.dto.MentorCardDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class WishlistService {
    private final JdbcTemplate jdbcTemplate;

    public WishlistService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MentorCardDto> getWishlistMentors(Integer menteeId) {
        return jdbcTemplate.query("""
                SELECT u.user_id, CONCAT(u.first_name, N' ', u.last_name) AS full_name, u.current_job, u.avatar,
                       m.expertise, m.experience_years, m.avg_star,
                       COALESCE(company.company_name, N'Independent') AS company_name,
                       COALESCE(c.category_name, N'Mentoring') AS category_name,
                       COALESCE(session_price.starting_price, 0) AS starting_price,
                       COALESCE(skill_names.skills, N'') AS skills
                FROM [mentor_wishlist] mw
                JOIN [mentor] m ON m.user_id = mw.mentor_id
                JOIN [user] u ON u.user_id = m.user_id
                OUTER APPLY (
                    SELECT TOP 1 co.company_name
                    FROM [work_for] wf
                    JOIN [company] co ON co.company_id = wf.company_id
                    WHERE wf.mentor_id = m.user_id
                    ORDER BY CASE WHEN wf.end_day IS NULL THEN 0 ELSE 1 END, wf.start_day DESC
                ) company
                OUTER APPLY (SELECT MIN(s.price) AS starting_price FROM [session] s WHERE s.mentor_id = m.user_id) session_price
                OUTER APPLY (
                    SELECT STRING_AGG(sk.skill_name, N'|') AS skills
                    FROM [mentor_skills] ms JOIN [skills] sk ON sk.skill_id = ms.skill_id
                    WHERE ms.mentor_id = m.user_id
                ) skill_names
                OUTER APPLY (
                    SELECT TOP 1 cat.category_name
                    FROM [mentor_skills] ms
                    JOIN [skill_category] sc ON sc.skill_id = ms.skill_id
                    JOIN [category] cat ON cat.category_id = sc.category_id
                    WHERE ms.mentor_id = m.user_id
                    ORDER BY cat.category_id
                ) c
                WHERE mw.mentee_id = ?
                ORDER BY mw.created_at DESC
                """, this::mapMentor, menteeId);
    }

    public void addMentor(Integer menteeId, Integer mentorId) {
        jdbcTemplate.update("""
                IF NOT EXISTS (SELECT 1 FROM [mentor_wishlist] WHERE mentee_id = ? AND mentor_id = ?)
                INSERT INTO [mentor_wishlist] (mentee_id, mentor_id) VALUES (?, ?)
                """, menteeId, mentorId, menteeId, mentorId);
    }

    public void removeMentor(Integer menteeId, Integer mentorId) {
        jdbcTemplate.update("DELETE FROM [mentor_wishlist] WHERE mentee_id = ? AND mentor_id = ?", menteeId, mentorId);
    }

    private MentorCardDto mapMentor(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        String skills = rs.getString("skills");
        List<String> skillList = skills == null || skills.isBlank()
                ? List.of()
                : Arrays.stream(skills.split("\\|"))
                .filter(item -> !item.isBlank())
                .toList();
        BigDecimal startingPrice = rs.getBigDecimal("starting_price");
        return new MentorCardDto(
                rs.getInt("user_id"),
                rs.getString("full_name"),
                rs.getString("current_job"),
                rs.getString("company_name"),
                rs.getString("category_name"),
                rs.getString("avatar"),
                rs.getString("expertise"),
                rs.getInt("experience_years"),
                rs.getBigDecimal("avg_star"),
                startingPrice == null ? BigDecimal.ZERO : startingPrice,
                skillList
        );
    }
}

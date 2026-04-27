package com.mentorhub.service;

import com.mentorhub.dto.MentorCardDto;
import com.mentorhub.dto.MentorDetailDto;
import com.mentorhub.dto.ReviewDto;
import com.mentorhub.dto.SessionDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class MentorService {
    private final JdbcTemplate jdbcTemplate;
    private final SessionService sessionService;

    public MentorService(JdbcTemplate jdbcTemplate, SessionService sessionService) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionService = sessionService;
    }

    public List<MentorCardDto> getMentors(String keyword, String category, String sortBy) {
        StringBuilder sql = new StringBuilder(baseMentorSql());
        sql.append(" WHERE u.[status] = N'Active' ");
        if (keyword != null && !keyword.isBlank()) {
            sql.append("""
                    AND (
                        LOWER(CONCAT(u.first_name, N' ', u.last_name)) LIKE ?
                        OR LOWER(m.expertise) LIKE ?
                        OR LOWER(COALESCE(skill_names.skills, N'')) LIKE ?
                        OR LOWER(COALESCE(c.category_name, N'')) LIKE ?
                    )
                    """);
        }
        if (category != null && !category.isBlank() && !"All".equalsIgnoreCase(category)) {
            sql.append(" AND LOWER(COALESCE(c.category_name, N'')) = ? ");
        }
        if ("rating".equalsIgnoreCase(sortBy)) {
            sql.append(" ORDER BY m.avg_star DESC, u.user_id DESC");
        } else if ("experience".equalsIgnoreCase(sortBy)) {
            sql.append(" ORDER BY m.experience_years DESC, u.user_id DESC");
        } else if ("priceLow".equalsIgnoreCase(sortBy)) {
            sql.append(" ORDER BY COALESCE(session_price.starting_price, 0), u.user_id DESC");
        } else {
            sql.append(" ORDER BY m.avg_star DESC, m.experience_years DESC, u.user_id DESC");
        }

        String like = "%" + (keyword == null ? "" : keyword.toLowerCase()) + "%";
        if (keyword != null && !keyword.isBlank() && category != null && !category.isBlank() && !"All".equalsIgnoreCase(category)) {
            return jdbcTemplate.query(sql.toString(), this::mapMentor, like, like, like, like, category.toLowerCase());
        }
        if (keyword != null && !keyword.isBlank()) {
            return jdbcTemplate.query(sql.toString(), this::mapMentor, like, like, like, like);
        }
        if (category != null && !category.isBlank() && !"All".equalsIgnoreCase(category)) {
            return jdbcTemplate.query(sql.toString(), this::mapMentor, category.toLowerCase());
        }
        return jdbcTemplate.query(sql.toString(), this::mapMentor);
    }

    public MentorDetailDto getMentorDetail(Integer mentorId) {
        MentorCardDto profile = jdbcTemplate.queryForObject(baseMentorSql() + " WHERE u.user_id = ?", this::mapMentor, mentorId);
        List<SessionDto> sessions = sessionService.getSessions(mentorId);
        List<ReviewDto> reviews = jdbcTemplate.query("""
                SELECT r.mentor_id, r.mentee_id, CONCAT(u.first_name, N' ', u.last_name) AS mentee_name,
                       r.comment, r.star, r.rated_date
                FROM [rate] r
                JOIN [user] u ON u.user_id = r.mentee_id
                WHERE r.mentor_id = ?
                ORDER BY r.rated_date DESC
                """, (rs, rowNum) -> new ReviewDto(
                rs.getInt("mentor_id"),
                rs.getInt("mentee_id"),
                rs.getString("mentee_name"),
                rs.getString("comment"),
                rs.getInt("star"),
                rs.getDate("rated_date") == null ? null : rs.getDate("rated_date").toLocalDate()
        ), mentorId);
        List<String> availableTimes = jdbcTemplate.query("""
                SELECT CONCAT(CONVERT(nvarchar(10), start_day, 120), N' ', CONVERT(nvarchar(5), start_time, 108),
                              N' - ', CONVERT(nvarchar(5), end_time, 108)) AS slot
                FROM [available_time]
                WHERE mentor_id = ?
                ORDER BY start_day, start_time
                """, (rs, rowNum) -> rs.getString("slot"), mentorId);
        return new MentorDetailDto(profile, sessions, reviews, availableTimes);
    }

    private String baseMentorSql() {
        return """
                SELECT u.user_id, CONCAT(u.first_name, N' ', u.last_name) AS full_name, u.current_job, u.avatar,
                       m.expertise, m.experience_years, m.avg_star,
                       COALESCE(company.company_name, N'Independent') AS company_name,
                       COALESCE(c.category_name, N'Mentoring') AS category_name,
                       COALESCE(session_price.starting_price, 0) AS starting_price,
                       COALESCE(skill_names.skills, N'') AS skills
                FROM [mentor] m
                JOIN [user] u ON u.user_id = m.user_id
                OUTER APPLY (
                    SELECT TOP 1 co.company_name
                    FROM [work_for] wf
                    JOIN [company] co ON co.company_id = wf.company_id
                    WHERE wf.mentor_id = m.user_id
                    ORDER BY CASE WHEN wf.end_day IS NULL THEN 0 ELSE 1 END, wf.start_day DESC
                ) company
                OUTER APPLY (
                    SELECT MIN(s.price) AS starting_price
                    FROM [session] s
                    WHERE s.mentor_id = m.user_id
                ) session_price
                OUTER APPLY (
                    SELECT STRING_AGG(sk.skill_name, N'|') AS skills
                    FROM [mentor_skills] ms
                    JOIN [skills] sk ON sk.skill_id = ms.skill_id
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
                """;
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

package com.mentorhub.service;

import com.mentorhub.dto.CourseDto;
import com.mentorhub.dto.CourseEnrollmentRequest;
import com.mentorhub.dto.CourseProgressRequest;
import com.mentorhub.dto.CourseRequest;
import com.mentorhub.dto.CourseStatusRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Service
public class CourseService {
    private final JdbcTemplate jdbcTemplate;

    public CourseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CourseDto> getAllCourses() {
        return jdbcTemplate.query(courseSql("ORDER BY c.course_id DESC"), this::mapCourse);
    }

    public CourseDto getCourse(Integer courseId) {
        return getCourseById(courseId);
    }

    public CourseDto createCourse(CourseRequest request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                    INSERT INTO [course] (mentor_id, title, description, [level], price, [status])
                    VALUES (?, ?, ?, ?, ?, N'Active')
                    """, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, request.mentorId());
            ps.setString(2, request.title());
            ps.setString(3, request.description());
            ps.setString(4, request.level());
            ps.setBigDecimal(5, request.price() == null ? java.math.BigDecimal.ZERO : request.price());
            return ps;
        }, keyHolder);
        return getCourseById(keyHolder.getKey().intValue());
    }

    public CourseDto updateCourse(Integer courseId, CourseRequest request) {
        int updated = jdbcTemplate.update("""
                UPDATE [course]
                SET mentor_id = ?, title = ?, description = ?, [level] = ?, price = ?
                WHERE course_id = ?
                """, request.mentorId(), request.title(), request.description(), request.level(),
                request.price() == null ? java.math.BigDecimal.ZERO : request.price(), courseId);
        if (updated == 0) throw new IllegalArgumentException("Không tìm thấy course_id = " + courseId);
        return getCourseById(courseId);
    }

    public CourseDto updateCourseStatus(Integer courseId, CourseStatusRequest request) {
        int updated = jdbcTemplate.update("UPDATE [course] SET [status] = ? WHERE course_id = ?", request.status(), courseId);
        if (updated == 0) throw new IllegalArgumentException("Không tìm thấy course_id = " + courseId);
        return getCourseById(courseId);
    }

    public void deleteCourse(Integer courseId) {
        Integer enrolled = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM [course_enrollment] WHERE course_id = ?", Integer.class, courseId);
        if (enrolled != null && enrolled > 0) {
            throw new IllegalArgumentException("Course đã có mentee đăng ký nên không thể xóa. Hãy đổi status thành Inactive.");
        }
        jdbcTemplate.update("DELETE FROM [course_lesson] WHERE course_id = ?", courseId);
        jdbcTemplate.update("DELETE FROM [course] WHERE course_id = ?", courseId);
    }

    @Transactional
    public CourseDto enrollCourse(Integer courseId, CourseEnrollmentRequest request) {
        int progress = normalizeProgress(request.progress() == null ? 0 : request.progress());
        jdbcTemplate.update("""
                IF NOT EXISTS (SELECT 1 FROM [course_enrollment] WHERE course_id = ? AND mentee_id = ?)
                    INSERT INTO [course_enrollment] (course_id, mentee_id, progress, [status]) VALUES (?, ?, ?, N'In Progress')
                ELSE
                    UPDATE [course_enrollment] SET progress = ?, [status] = N'In Progress' WHERE course_id = ? AND mentee_id = ?
                """, courseId, request.menteeId(), courseId, request.menteeId(), progress, progress, courseId, request.menteeId());
        return getMenteeCourse(courseId, request.menteeId());
    }

    public CourseDto updateEnrollmentProgress(Integer courseId, Integer menteeId, CourseProgressRequest request) {
        int progress = normalizeProgress(request.progress());
        String status = request.status() == null || request.status().isBlank()
                ? (progress >= 100 ? "Completed" : "In Progress")
                : request.status();
        int updated = jdbcTemplate.update("""
                UPDATE [course_enrollment]
                SET progress = ?, [status] = ?
                WHERE course_id = ? AND mentee_id = ?
                """, progress, status, courseId, menteeId);
        if (updated == 0) throw new IllegalArgumentException("Mentee chưa enroll course này.");
        return getMenteeCourse(courseId, menteeId);
    }

    public List<CourseDto> getMenteeCourses(Integer menteeId) {
        return jdbcTemplate.query("""
                SELECT c.course_id, c.mentor_id, CONCAT(u.first_name, N' ', u.last_name) AS mentor_name,
                       c.title, c.description, c.[level], c.price, c.[status],
                       COALESCE(lesson_count.lessons, 0) AS lessons,
                       COALESCE(ce.progress, 0) AS progress
                FROM [course_enrollment] ce
                JOIN [course] c ON c.course_id = ce.course_id
                JOIN [user] u ON u.user_id = c.mentor_id
                OUTER APPLY (
                    SELECT COUNT(1) AS lessons FROM [course_lesson] cl WHERE cl.course_id = c.course_id
                ) lesson_count
                WHERE ce.mentee_id = ?
                ORDER BY ce.enrolled_date DESC, c.course_id DESC
                """, this::mapCourse, menteeId);
    }

    public List<CourseDto> getMentorCourses(Integer mentorId) {
        return jdbcTemplate.query(courseSql("WHERE c.mentor_id = ? ORDER BY c.course_id DESC"), this::mapCourse, mentorId);
    }

    private CourseDto getMenteeCourse(Integer courseId, Integer menteeId) {
        return jdbcTemplate.queryForObject("""
                SELECT c.course_id, c.mentor_id, CONCAT(u.first_name, N' ', u.last_name) AS mentor_name,
                       c.title, c.description, c.[level], c.price, c.[status],
                       COALESCE(lesson_count.lessons, 0) AS lessons,
                       COALESCE(ce.progress, 0) AS progress
                FROM [course_enrollment] ce
                JOIN [course] c ON c.course_id = ce.course_id
                JOIN [user] u ON u.user_id = c.mentor_id
                OUTER APPLY (
                    SELECT COUNT(1) AS lessons FROM [course_lesson] cl WHERE cl.course_id = c.course_id
                ) lesson_count
                WHERE ce.course_id = ? AND ce.mentee_id = ?
                """, this::mapCourse, courseId, menteeId);
    }

    private CourseDto getCourseById(Integer courseId) {
        return jdbcTemplate.queryForObject(courseSql("WHERE c.course_id = ?"), this::mapCourse, courseId);
    }

    private String courseSql(String whereClause) {
        return """
                SELECT c.course_id, c.mentor_id, CONCAT(u.first_name, N' ', u.last_name) AS mentor_name,
                       c.title, c.description, c.[level], c.price, c.[status],
                       COALESCE(lesson_count.lessons, 0) AS lessons,
                       0 AS progress
                FROM [course] c
                JOIN [user] u ON u.user_id = c.mentor_id
                OUTER APPLY (
                    SELECT COUNT(1) AS lessons FROM [course_lesson] cl WHERE cl.course_id = c.course_id
                ) lesson_count
                """ + whereClause;
    }

    private int normalizeProgress(Integer value) {
        if (value == null) return 0;
        return Math.max(0, Math.min(100, value));
    }

    private CourseDto mapCourse(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new CourseDto(
                rs.getInt("course_id"),
                rs.getInt("mentor_id"),
                rs.getString("mentor_name"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("level"),
                rs.getBigDecimal("price"),
                rs.getInt("lessons"),
                rs.getInt("progress"),
                rs.getString("status")
        );
    }
}

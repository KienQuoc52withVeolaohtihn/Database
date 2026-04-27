package com.mentorhub.service;

import com.mentorhub.dto.BookingDto;
import com.mentorhub.dto.BookingRequest;
import com.mentorhub.dto.BookingResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
    private final JdbcTemplate jdbcTemplate;

    public BookingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookingDto> getAllBookings() {
        return jdbcTemplate.query(bookingSql("ORDER BY ms.[date] DESC, ms.start_time DESC, ms.mentee_session_id DESC"), this::mapBooking);
    }

    public List<BookingDto> getBookingsByMentee(Integer menteeId) {
        return jdbcTemplate.query(bookingSql("WHERE ms.mentee_id = ? ORDER BY ms.[date] DESC, ms.start_time DESC"), this::mapBooking, menteeId);
    }

    public List<BookingDto> getBookingsByMentor(Integer mentorId) {
        return jdbcTemplate.query(bookingSql("WHERE s.mentor_id = ? ORDER BY ms.[date] DESC, ms.start_time DESC"), this::mapBooking, mentorId);
    }

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        BigDecimal sessionPrice = jdbcTemplate.queryForObject(
                "SELECT price FROM [session] WHERE session_id = ?",
                BigDecimal.class,
                request.sessionId()
        );
        if (sessionPrice == null) sessionPrice = BigDecimal.ZERO;

        BigDecimal discountValue = BigDecimal.ZERO;
        if (request.discountId() != null) {
            BigDecimal value = jdbcTemplate.queryForObject(
                    "SELECT discount_value FROM [discount] WHERE discount_id = ? AND [status] = N'Active'",
                    BigDecimal.class,
                    request.discountId()
            );
            if (value != null) discountValue = value;
        }
        BigDecimal total = sessionPrice.subtract(discountValue).max(BigDecimal.ZERO);
        String meetingLink = "https://meet.mentorhub.local/" + UUID.randomUUID();

        KeyHolder bookingKey = new GeneratedKeyHolder();
        BigDecimal finalTotal = total;
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                    INSERT INTO [mentee_session] (mentee_id, session_id, discount_id, [date], start_time, end_time, link)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, request.menteeId());
            ps.setInt(2, request.sessionId());
            if (request.discountId() == null) ps.setNull(3, java.sql.Types.INTEGER); else ps.setInt(3, request.discountId());
            ps.setDate(4, java.sql.Date.valueOf(request.date()));
            ps.setTime(5, java.sql.Time.valueOf(request.startTime()));
            ps.setTime(6, java.sql.Time.valueOf(request.endTime()));
            ps.setString(7, meetingLink);
            return ps;
        }, bookingKey);
        Integer bookingId = bookingKey.getKey().intValue();

        KeyHolder paymentKey = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                    INSERT INTO [payment] (mentee_session_id, [date], total_money, [status])
                    VALUES (?, CONVERT(date, SYSUTCDATETIME()), ?, N'Unpaid')
                    """, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, bookingId);
            ps.setBigDecimal(2, finalTotal);
            return ps;
        }, paymentKey);

        return new BookingResponse(
                bookingId,
                paymentKey.getKey().intValue(),
                "Unpaid",
                total,
                request.date(),
                request.startTime(),
                request.endTime(),
                meetingLink
        );
    }

    private String bookingSql(String whereClause) {
        return """
                SELECT ms.mentee_session_id, ms.mentee_id,
                       CONCAT(mentee_user.first_name, N' ', mentee_user.last_name) AS mentee_name,
                       s.mentor_id,
                       CONCAT(mentor_user.first_name, N' ', mentor_user.last_name) AS mentor_name,
                       ms.session_id, s.session_name,
                       p.payment_id, p.[status] AS payment_status, p.total_money,
                       ms.[date], ms.start_time, ms.end_time, ms.link
                FROM [mentee_session] ms
                JOIN [session] s ON s.session_id = ms.session_id
                JOIN [user] mentee_user ON mentee_user.user_id = ms.mentee_id
                JOIN [user] mentor_user ON mentor_user.user_id = s.mentor_id
                LEFT JOIN [payment] p ON p.mentee_session_id = ms.mentee_session_id
                """ + whereClause;
    }

    private BookingDto mapBooking(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Time start = rs.getTime("start_time");
        Time end = rs.getTime("end_time");
        LocalTime startTime = start == null ? null : start.toLocalTime();
        LocalTime endTime = end == null ? null : end.toLocalTime();
        return new BookingDto(
                rs.getInt("mentee_session_id"),
                rs.getInt("mentee_id"),
                rs.getString("mentee_name"),
                rs.getInt("mentor_id"),
                rs.getString("mentor_name"),
                rs.getInt("session_id"),
                rs.getString("session_name"),
                rs.getObject("payment_id") == null ? null : rs.getInt("payment_id"),
                rs.getString("payment_status"),
                rs.getBigDecimal("total_money"),
                rs.getDate("date") == null ? null : rs.getDate("date").toLocalDate(),
                startTime,
                endTime,
                rs.getString("link")
        );
    }
}

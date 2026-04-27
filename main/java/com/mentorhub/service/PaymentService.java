package com.mentorhub.service;

import com.mentorhub.dto.PaymentDto;
import com.mentorhub.dto.PaymentStatusRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final JdbcTemplate jdbcTemplate;

    public PaymentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PaymentDto> getPaymentsByUser(Integer userId) {
        return jdbcTemplate.query(paymentSql("""
                WHERE ms.mentee_id = ? OR s.mentor_id = ?
                ORDER BY p.[date] DESC, p.payment_id DESC
                """), this::mapPayment, userId, userId);
    }

    public List<PaymentDto> getAllPayments() {
        return jdbcTemplate.query(paymentSql("ORDER BY p.[date] DESC, p.payment_id DESC"), this::mapPayment);
    }

    public PaymentDto updatePaymentStatus(Integer paymentId, PaymentStatusRequest request) {
        String method = request.method() == null || request.method().isBlank()
                ? ("Paid".equalsIgnoreCase(request.status()) ? "Card" : "Pending")
                : request.method();
        jdbcTemplate.update("""
                UPDATE [payment]
                SET [status] = ?,
                    payment_method = ?,
                    paid_at = CASE WHEN ? = N'Paid' THEN COALESCE(paid_at, SYSUTCDATETIME()) ELSE NULL END
                WHERE payment_id = ?
                """, request.status(), method, request.status(), paymentId);
        return jdbcTemplate.queryForObject(paymentSql("WHERE p.payment_id = ?"), this::mapPayment, paymentId);
    }

    private String paymentSql(String whereClause) {
        return """
                SELECT p.payment_id, p.mentee_session_id, p.[date], p.total_money, p.[status],
                       COALESCE(p.payment_method, CASE WHEN p.[status] = N'Paid' THEN N'Card' ELSE N'Pending' END) AS payment_method,
                       ms.mentee_id, CONCAT(mentee_user.first_name, N' ', mentee_user.last_name) AS mentee_name,
                       s.mentor_id, CONCAT(mentor_user.first_name, N' ', mentor_user.last_name) AS mentor_name,
                       s.session_name
                FROM [payment] p
                JOIN [mentee_session] ms ON ms.mentee_session_id = p.mentee_session_id
                JOIN [session] s ON s.session_id = ms.session_id
                JOIN [user] mentee_user ON mentee_user.user_id = ms.mentee_id
                JOIN [user] mentor_user ON mentor_user.user_id = s.mentor_id
                """ + whereClause;
    }

    private PaymentDto mapPayment(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new PaymentDto(
                rs.getInt("payment_id"),
                rs.getInt("mentee_session_id"),
                rs.getInt("mentee_id"),
                rs.getString("mentee_name"),
                rs.getInt("mentor_id"),
                rs.getString("mentor_name"),
                rs.getString("session_name"),
                rs.getBigDecimal("total_money"),
                rs.getString("status"),
                rs.getString("payment_method"),
                rs.getDate("date") == null ? null : rs.getDate("date").toLocalDate()
        );
    }
}

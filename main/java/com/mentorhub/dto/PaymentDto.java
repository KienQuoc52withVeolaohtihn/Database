package com.mentorhub.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentDto(
        Integer id,
        Integer menteeSessionId,
        Integer menteeId,
        String menteeName,
        Integer mentorId,
        String mentorName,
        String sessionName,
        BigDecimal amount,
        String status,
        String method,
        LocalDate date
) {}

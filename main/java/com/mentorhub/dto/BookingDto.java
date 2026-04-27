package com.mentorhub.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record BookingDto(
        Integer id,
        Integer menteeId,
        String menteeName,
        Integer mentorId,
        String mentorName,
        Integer sessionId,
        String sessionName,
        Integer paymentId,
        String paymentStatus,
        BigDecimal totalMoney,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        String meetingLink
) {}

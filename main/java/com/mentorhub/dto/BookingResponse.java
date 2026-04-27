package com.mentorhub.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record BookingResponse(
        Integer bookingId,
        Integer paymentId,
        String status,
        BigDecimal totalMoney,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        String meetingLink
) {}

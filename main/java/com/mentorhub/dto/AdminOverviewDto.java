package com.mentorhub.dto;

import java.math.BigDecimal;

public record AdminOverviewDto(
        long totalUsers,
        long totalMentors,
        long totalMentees,
        long totalBookings,
        BigDecimal revenue
) {}

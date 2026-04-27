package com.mentorhub.dto;

import java.math.BigDecimal;

public record SessionDto(
        Integer id,
        Integer mentorId,
        String sessionName,
        String description,
        Integer duration,
        BigDecimal price
) {}

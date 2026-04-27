package com.mentorhub.dto;

import java.math.BigDecimal;

public record CourseDto(
        Integer id,
        Integer mentorId,
        String mentorName,
        String title,
        String description,
        String level,
        BigDecimal price,
        Integer lessons,
        Integer progress,
        String status
) {}

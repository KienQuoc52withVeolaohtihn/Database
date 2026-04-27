package com.mentorhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CourseRequest(
        @NotNull Integer mentorId,
        @NotBlank String title,
        String description,
        String level,
        BigDecimal price
) {}

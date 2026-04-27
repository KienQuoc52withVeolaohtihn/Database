package com.mentorhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record SessionRequest(
        @NotNull Integer mentorId,
        @NotBlank String sessionName,
        String description,
        @NotNull Integer duration,
        @NotNull BigDecimal price
) {}

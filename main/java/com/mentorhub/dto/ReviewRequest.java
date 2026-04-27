package com.mentorhub.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest(
        @NotNull Integer mentorId,
        @NotNull Integer menteeId,
        String comment,
        @NotNull @Min(1) @Max(5) Integer star
) {}

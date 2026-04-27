package com.mentorhub.dto;

import jakarta.validation.constraints.NotNull;

public record CourseEnrollmentRequest(
        @NotNull Integer menteeId,
        Integer progress
) {}

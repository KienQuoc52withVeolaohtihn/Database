package com.mentorhub.dto;

import jakarta.validation.constraints.NotNull;

public record CourseProgressRequest(
        @NotNull Integer progress,
        String status
) {}

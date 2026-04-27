package com.mentorhub.dto;

import jakarta.validation.constraints.NotBlank;

public record CourseStatusRequest(
        @NotBlank String status
) {}

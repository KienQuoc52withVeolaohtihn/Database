package com.mentorhub.dto;

import jakarta.validation.constraints.NotBlank;

public record NotificationRequest(
        Integer adminId,
        @NotBlank String targetRole,
        @NotBlank String title,
        @NotBlank String content
) {}

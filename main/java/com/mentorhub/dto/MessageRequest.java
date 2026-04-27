package com.mentorhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MessageRequest(
        @NotNull Integer mentorId,
        @NotNull Integer menteeId,
        @NotBlank String senderRole,
        @NotBlank String content
) {}

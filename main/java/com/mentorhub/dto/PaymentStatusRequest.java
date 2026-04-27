package com.mentorhub.dto;

import jakarta.validation.constraints.NotBlank;

public record PaymentStatusRequest(
        @NotBlank String status,
        String method
) {}

package com.mentorhub.dto;

import jakarta.validation.constraints.NotNull;

public record WishlistMentorRequest(
        @NotNull Integer menteeId,
        @NotNull Integer mentorId
) {}

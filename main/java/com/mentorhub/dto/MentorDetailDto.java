package com.mentorhub.dto;

import java.util.List;

public record MentorDetailDto(
        MentorCardDto profile,
        List<SessionDto> sessions,
        List<ReviewDto> reviews,
        List<String> availableTimes
) {}

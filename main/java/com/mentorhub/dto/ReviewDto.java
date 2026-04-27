package com.mentorhub.dto;

import java.time.LocalDate;

public record ReviewDto(
        Integer mentorId,
        Integer menteeId,
        String menteeName,
        String comment,
        Integer star,
        LocalDate ratedDate
) {}

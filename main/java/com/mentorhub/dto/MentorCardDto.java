package com.mentorhub.dto;

import java.math.BigDecimal;
import java.util.List;

public record MentorCardDto(
        Integer id,
        String fullName,
        String title,
        String company,
        String category,
        String avatar,
        String expertise,
        Integer experienceYears,
        BigDecimal avgStar,
        BigDecimal startingPrice,
        List<String> skills
) {}

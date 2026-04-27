package com.mentorhub.dto;

import com.mentorhub.model.UserRole;

public record LoginResponse(
        Integer id,
        String fullName,
        String name,
        String email,
        UserRole role,
        String status,
        String token,
        Integer mentorId
) {}

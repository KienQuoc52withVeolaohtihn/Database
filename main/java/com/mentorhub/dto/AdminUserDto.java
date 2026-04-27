package com.mentorhub.dto;

import com.mentorhub.model.UserRole;

public record AdminUserDto(
        Integer id,
        String fullName,
        String email,
        UserRole role,
        String title,
        String status
) {}

package com.mentorhub.dto;

import java.time.LocalDateTime;

public record MessageDto(
        Integer id,
        Integer mentorId,
        String mentorName,
        Integer menteeId,
        String menteeName,
        String senderRole,
        String content,
        LocalDateTime sentAt
) {}

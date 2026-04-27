package com.mentorhub.dto;

import java.time.LocalDateTime;

public record MessageThreadDto(
        Integer mentorId,
        String mentorName,
        Integer menteeId,
        String menteeName,
        String lastMessage,
        String lastSenderRole,
        LocalDateTime lastSentAt,
        long totalMessages
) {}

package com.mentorhub.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record NotificationDto(
        Integer id,
        Integer adminId,
        String senderName,
        String targetRole,
        String title,
        String content,
        LocalDate sentDate,
        LocalTime sentTime,
        Boolean isRead
) {}

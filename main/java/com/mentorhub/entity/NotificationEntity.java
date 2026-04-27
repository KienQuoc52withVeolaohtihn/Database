package com.mentorhub.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "notification")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Integer id;

    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "target_role")
    private String targetRole;

    private String title;

    @Column(columnDefinition = "nvarchar(max)")
    private String content;

    @Column(name = "sent_date")
    private LocalDate sentDate;

    @Column(name = "sent_time")
    private LocalTime sentTime;
}

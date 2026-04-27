package com.mentorhub.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer id;

    @Column(name = "mentor_id")
    private Integer mentorId;

    @Column(name = "mentee_id")
    private Integer menteeId;

    @Column(name = "sender_role")
    private String senderRole;

    @Column(columnDefinition = "nvarchar(max)")
    private String content;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;
}

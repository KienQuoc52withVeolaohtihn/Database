package com.mentorhub.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "mentee_session")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentee_session_id")
    private Integer id;

    @Column(name = "mentee_id")
    private Integer menteeId;

    @Column(name = "session_id")
    private Integer sessionId;

    @Column(name = "discount_id")
    private Integer discountId;

    private LocalDate date;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    private String link;
}

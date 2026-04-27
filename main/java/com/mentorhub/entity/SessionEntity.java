package com.mentorhub.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "session")
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer id;

    @Column(name = "mentor_id")
    private Integer mentorId;

    @Column(name = "session_name")
    private String sessionName;

    @Column(columnDefinition = "nvarchar(max)")
    private String description;

    private Integer duration;
    private BigDecimal price;
}

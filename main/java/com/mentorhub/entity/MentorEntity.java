package com.mentorhub.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "mentor")
public class MentorEntity {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(columnDefinition = "nvarchar(max)")
    private String expertise;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "avg_star")
    private BigDecimal avgStar;
}

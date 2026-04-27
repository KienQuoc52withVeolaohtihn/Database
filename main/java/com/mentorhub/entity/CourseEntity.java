package com.mentorhub.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer id;

    @Column(name = "mentor_id")
    private Integer mentorId;

    private String title;

    @Column(columnDefinition = "nvarchar(max)")
    private String description;

    private String level;
    private BigDecimal price;
}

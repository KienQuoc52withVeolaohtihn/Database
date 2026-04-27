package com.mentorhub.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "course_enrollment")
public class CourseEnrollmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Integer id;

    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "mentee_id")
    private Integer menteeId;

    @Column(name = "enrolled_date")
    private LocalDate enrolledDate;

    private Integer progress;
    private String status;
}

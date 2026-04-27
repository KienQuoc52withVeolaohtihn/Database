package com.mentorhub.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mentee")
public class MenteeEntity {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "learning_goal", columnDefinition = "nvarchar(max)")
    private String learningGoal;

    private String level;
}

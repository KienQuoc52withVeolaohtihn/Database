package com.mentorhub.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class SkillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Integer id;

    @Column(name = "skill_name")
    private String skillName;
}

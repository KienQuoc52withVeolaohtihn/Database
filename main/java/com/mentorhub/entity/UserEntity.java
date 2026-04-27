package com.mentorhub.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "[user]")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String status;

    // Role is derived from related tables in this database design:
    // mentor.user_id => MENTOR, mentee.user_id => MENTEE. Admin records live in [admin].
}

package com.mentorhub.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer id;

    @Column(name = "mentee_session_id")
    private Integer menteeSessionId;

    private LocalDate date;

    @Column(name = "total_money")
    private BigDecimal totalMoney;

    private String status;
}

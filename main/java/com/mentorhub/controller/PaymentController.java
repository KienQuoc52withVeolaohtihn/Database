package com.mentorhub.controller;

import com.mentorhub.dto.PaymentDto;
import com.mentorhub.dto.PaymentStatusRequest;
import com.mentorhub.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(paymentService.getPaymentsByUser(userId));
    }

    @PatchMapping("/{paymentId}/status")
    public ResponseEntity<PaymentDto> updatePaymentStatus(
            @PathVariable Integer paymentId,
            @Valid @RequestBody PaymentStatusRequest request
    ) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(paymentId, request));
    }
}

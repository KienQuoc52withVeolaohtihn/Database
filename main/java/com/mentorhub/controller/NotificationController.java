package com.mentorhub.controller;

import com.mentorhub.dto.NotificationDto;
import com.mentorhub.dto.NotificationRequest;
import com.mentorhub.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<NotificationDto> sendNotification(@Valid @RequestBody NotificationRequest request) {
        return ResponseEntity.ok(notificationService.sendNotification(request));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsForUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(userId));
    }

    @GetMapping("/roles/{targetRole}")
    public ResponseEntity<List<NotificationDto>> getNotificationsForRole(@PathVariable String targetRole) {
        return ResponseEntity.ok(notificationService.getNotificationsForRole(targetRole));
    }

    @PatchMapping("/users/{userId}/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Integer userId, @PathVariable Integer notificationId) {
        notificationService.markAsRead(userId, notificationId);
        return ResponseEntity.noContent().build();
    }
}

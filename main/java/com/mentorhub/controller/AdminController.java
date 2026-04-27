package com.mentorhub.controller;

import com.mentorhub.dto.AdminOverviewDto;
import com.mentorhub.dto.AdminUserDto;
import com.mentorhub.dto.ApprovalRequest;
import com.mentorhub.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/overview")
    public ResponseEntity<AdminOverviewDto> getOverview() {
        return ResponseEntity.ok(adminService.getOverview());
    }

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserDto>> getUsers(@RequestParam(required = false) String status) {
        return ResponseEntity.ok(adminService.getUsers(status));
    }

    @PostMapping("/users/{userId}/approve")
    public ResponseEntity<AdminUserDto> approveUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(adminService.approveUser(userId));
    }

    @PatchMapping("/users/{userId}/status")
    public ResponseEntity<AdminUserDto> updateUserStatus(
            @PathVariable Integer userId,
            @RequestBody ApprovalRequest request
    ) {
        return ResponseEntity.ok(adminService.updateUserStatus(userId, request.status()));
    }
}

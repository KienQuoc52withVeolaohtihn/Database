package com.mentorhub.controller;

import com.mentorhub.dto.SessionDto;
import com.mentorhub.dto.SessionRequest;
import com.mentorhub.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<List<SessionDto>> getSessions(@RequestParam(required = false) Integer mentorId) {
        return ResponseEntity.ok(sessionService.getSessions(mentorId));
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionDto> getSessionById(@PathVariable Integer sessionId) {
        return ResponseEntity.ok(sessionService.getSessionById(sessionId));
    }

    @PostMapping
    public ResponseEntity<SessionDto> createSession(@Valid @RequestBody SessionRequest request) {
        return ResponseEntity.ok(sessionService.createSession(request));
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<SessionDto> updateSession(@PathVariable Integer sessionId, @Valid @RequestBody SessionRequest request) {
        return ResponseEntity.ok(sessionService.updateSession(sessionId, request));
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable Integer sessionId) {
        sessionService.deleteSession(sessionId);
        return ResponseEntity.noContent().build();
    }
}

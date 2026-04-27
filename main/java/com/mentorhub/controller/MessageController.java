package com.mentorhub.controller;

import com.mentorhub.dto.MessageDto;
import com.mentorhub.dto.MessageRequest;
import com.mentorhub.dto.MessageThreadDto;
import com.mentorhub.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageDto> sendMessage(@Valid @RequestBody MessageRequest request) {
        return ResponseEntity.ok(messageService.sendMessage(request));
    }

    @GetMapping("/mentors/{mentorId}")
    public ResponseEntity<List<MessageDto>> getMentorMessages(@PathVariable Integer mentorId) {
        return ResponseEntity.ok(messageService.getMentorMessages(mentorId));
    }

    @GetMapping("/mentees/{menteeId}")
    public ResponseEntity<List<MessageDto>> getMenteeMessages(@PathVariable Integer menteeId) {
        return ResponseEntity.ok(messageService.getMenteeMessages(menteeId));
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<MessageDto>> getConversation(
            @RequestParam Integer mentorId,
            @RequestParam Integer menteeId
    ) {
        return ResponseEntity.ok(messageService.getConversation(mentorId, menteeId));
    }

    @GetMapping("/mentors/{mentorId}/threads")
    public ResponseEntity<List<MessageThreadDto>> getMentorThreads(@PathVariable Integer mentorId) {
        return ResponseEntity.ok(messageService.getMentorThreads(mentorId));
    }

    @GetMapping("/mentees/{menteeId}/threads")
    public ResponseEntity<List<MessageThreadDto>> getMenteeThreads(@PathVariable Integer menteeId) {
        return ResponseEntity.ok(messageService.getMenteeThreads(menteeId));
    }
}

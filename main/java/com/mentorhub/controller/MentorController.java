package com.mentorhub.controller;

import com.mentorhub.dto.MentorCardDto;
import com.mentorhub.dto.MentorDetailDto;
import com.mentorhub.service.MentorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {
    private final MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @GetMapping
    public ResponseEntity<List<MentorCardDto>> getMentors(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "recommended") String sortBy
    ) {
        return ResponseEntity.ok(mentorService.getMentors(keyword, category, sortBy));
    }

    @GetMapping("/{mentorId}")
    public ResponseEntity<MentorDetailDto> getMentorDetail(@PathVariable Integer mentorId) {
        return ResponseEntity.ok(mentorService.getMentorDetail(mentorId));
    }
}

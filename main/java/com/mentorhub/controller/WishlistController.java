package com.mentorhub.controller;

import com.mentorhub.dto.MentorCardDto;
import com.mentorhub.dto.WishlistMentorRequest;
import com.mentorhub.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/mentees/{menteeId}/mentors")
    public ResponseEntity<List<MentorCardDto>> getWishlistMentors(@PathVariable Integer menteeId) {
        return ResponseEntity.ok(wishlistService.getWishlistMentors(menteeId));
    }

    @PostMapping("/mentors")
    public ResponseEntity<Void> addMentorToWishlist(@Valid @RequestBody WishlistMentorRequest request) {
        wishlistService.addMentor(request.menteeId(), request.mentorId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/mentees/{menteeId}/mentors/{mentorId}")
    public ResponseEntity<Void> removeMentorFromWishlist(@PathVariable Integer menteeId, @PathVariable Integer mentorId) {
        wishlistService.removeMentor(menteeId, mentorId);
        return ResponseEntity.noContent().build();
    }
}

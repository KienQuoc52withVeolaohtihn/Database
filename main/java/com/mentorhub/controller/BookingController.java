package com.mentorhub.controller;

import com.mentorhub.dto.BookingDto;
import com.mentorhub.dto.BookingRequest;
import com.mentorhub.dto.BookingResponse;
import com.mentorhub.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/mentees/{menteeId}")
    public ResponseEntity<List<BookingDto>> getBookingsByMentee(@PathVariable Integer menteeId) {
        return ResponseEntity.ok(bookingService.getBookingsByMentee(menteeId));
    }

    @GetMapping("/mentors/{mentorId}")
    public ResponseEntity<List<BookingDto>> getBookingsByMentor(@PathVariable Integer mentorId) {
        return ResponseEntity.ok(bookingService.getBookingsByMentor(mentorId));
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }
}

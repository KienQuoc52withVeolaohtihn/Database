package com.mentorhub.controller;

import com.mentorhub.dto.CourseDto;
import com.mentorhub.dto.CourseEnrollmentRequest;
import com.mentorhub.dto.CourseProgressRequest;
import com.mentorhub.dto.CourseRequest;
import com.mentorhub.dto.CourseStatusRequest;
import com.mentorhub.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable Integer courseId) {
        return ResponseEntity.ok(courseService.getCourse(courseId));
    }

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.createCourse(request));
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Integer courseId, @Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.updateCourse(courseId, request));
    }

    @PatchMapping("/{courseId}/status")
    public ResponseEntity<CourseDto> updateCourseStatus(@PathVariable Integer courseId, @Valid @RequestBody CourseStatusRequest request) {
        return ResponseEntity.ok(courseService.updateCourseStatus(courseId, request));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Integer courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<CourseDto> enrollCourse(@PathVariable Integer courseId, @Valid @RequestBody CourseEnrollmentRequest request) {
        return ResponseEntity.ok(courseService.enrollCourse(courseId, request));
    }

    @PatchMapping("/{courseId}/mentees/{menteeId}/progress")
    public ResponseEntity<CourseDto> updateEnrollmentProgress(
            @PathVariable Integer courseId,
            @PathVariable Integer menteeId,
            @Valid @RequestBody CourseProgressRequest request
    ) {
        return ResponseEntity.ok(courseService.updateEnrollmentProgress(courseId, menteeId, request));
    }

    @GetMapping("/mentees/{menteeId}")
    public ResponseEntity<List<CourseDto>> getMenteeCourses(@PathVariable Integer menteeId) {
        return ResponseEntity.ok(courseService.getMenteeCourses(menteeId));
    }

    @GetMapping("/mentors/{mentorId}")
    public ResponseEntity<List<CourseDto>> getMentorCourses(@PathVariable Integer mentorId) {
        return ResponseEntity.ok(courseService.getMentorCourses(mentorId));
    }
}

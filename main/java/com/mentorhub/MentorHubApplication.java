package com.mentorhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MentorHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(MentorHubApplication.class, args);
    }
}

@RestController
@RequestMapping("/api/health")
class HealthController {
    @GetMapping
    public String health() {
        return "MentorHub backend is running";
    }
}

package com.example.resume.controller;

import com.example.resume.service.JobApiService;
import com.example.resume.service.JobApiService.JobListing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobApiService jobApiService;

    @GetMapping("/search")
    public ResponseEntity<List<JobListing>> searchJobs(@RequestParam String jobTitle) {
        List<JobListing> jobs = jobApiService.fetchJobs(jobTitle);
        return ResponseEntity.ok(jobs);
    }
}
package com.example.resume.controller;

import com.example.resume.entity.Resume;
import com.example.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<Resume> uploadResume(@RequestParam String email,
                                                @RequestParam String resumeText) {
        Resume resume = resumeService.saveResume(email, resumeText);
        return ResponseEntity.ok(resume);
    }

    @GetMapping
    public ResponseEntity<List<Resume>> getResumes(@RequestParam String email) {
        List<Resume> resumes = resumeService.getResumesForUser(email);
        return ResponseEntity.ok(resumes);
    }
}
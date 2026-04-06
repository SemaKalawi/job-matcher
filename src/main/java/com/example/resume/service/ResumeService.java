package com.example.resume.service;

import com.example.resume.entity.Resume;
import com.example.resume.entity.User;
import com.example.resume.repository.ResumeRepository;
import com.example.resume.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public Resume saveResume(String email, String resumeText) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        Resume resume = new Resume();
        resume.setUser(user);
        resume.setRawText(resumeText);

        return resumeRepository.save(resume);
    }

    public List<Resume> getResumesForUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        return resumeRepository.findByUserId(user.getId());
    }
}
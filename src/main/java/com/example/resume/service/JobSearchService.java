package com.example.resume.service;

import com.example.resume.entity.JobMatch;
import com.example.resume.entity.JobSearch;
import com.example.resume.entity.User;
import com.example.resume.repository.JobMatchRepository;
import com.example.resume.repository.JobSearchRepository;
import com.example.resume.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobSearchService {

    private final JobSearchRepository jobSearchRepository;
    private final JobMatchRepository jobMatchRepository;
    private final UserRepository userRepository;

    public JobSearch createJobSearch(String email, String jobTitle) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        JobSearch search = new JobSearch();
        search.setUser(user);
        search.setJobTitle(jobTitle);

        return jobSearchRepository.save(search);
    }

    public JobMatch saveJobMatch(JobSearch jobSearch, String jobTitle,
                                  String company, String description,
                                  int matchScore, String suggestions) {
        JobMatch match = new JobMatch();
        match.setJobSearch(jobSearch);
        match.setJobTitle(jobTitle);
        match.setCompany(company);
        match.setDescription(description);
        match.setMatchScore(matchScore);
        match.setSuggestions(suggestions);

        return jobMatchRepository.save(match);
    }

    public List<JobMatch> getMatchesForSearch(Long jobSearchId) {
        return jobMatchRepository.findByJobSearchId(jobSearchId);
    }
}
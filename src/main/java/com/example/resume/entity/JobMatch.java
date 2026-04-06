package com.example.resume.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "job_matches")
public class JobMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_search_id")
    private JobSearch jobSearch;

    private String jobTitle;
    private String company;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer matchScore;

    @Column(columnDefinition = "TEXT")
    private String suggestions;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
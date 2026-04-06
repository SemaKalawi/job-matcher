package com.example.resume.repository;

import com.example.resume.entity.JobSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobSearchRepository extends JpaRepository<JobSearch, Long> {
    List<JobSearch> findByUserId(Long userId);
}
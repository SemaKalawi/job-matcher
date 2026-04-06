package com.example.resume.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class JobApiService {

    @Value("${adzuna.app.id}")
    private String appId;

    @Value("${adzuna.app.key}")
    private String appKey;

    private final WebClient webClient = WebClient.create();

    public List<JobListing> fetchJobs(String jobTitle) {
    String url = "https://api.adzuna.com/v1/api/jobs/us/search/1"
        + "?app_id=" + appId
        + "&app_key=" + appKey
        + "&what=" + jobTitle.replace(" ", "+")
        + "&results_per_page=5"
        + "&content-type=application/json";

        String response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("ADZUNA RESPONSE: " + response);

        return parseJobs(response);
    }

private List<JobListing> parseJobs(String json) {
    List<JobListing> jobs = new ArrayList<>();
    if (json == null) return jobs;

    String[] results = json.split("\"__CLASS__\":\"Adzuna::API::Response::Job\"");
    for (int i = 1; i < results.length; i++) {
        String chunk = results[i];
        JobListing job = new JobListing();
        job.setTitle(extractField(chunk, "title"));
        job.setCompany(extractCompany(chunk));
        job.setDescription(extractField(chunk, "description"));
        jobs.add(job);
    }
    return jobs;
}

private String extractCompany(String json) {
    Pattern pattern = Pattern.compile(
        "\"company\"\\s*:\\s*\\{[^}]*\"display_name\"\\s*:\\s*\"(.*?)\"");
    Matcher matcher = pattern.matcher(json);
    return matcher.find() ? matcher.group(1) : "N/A";
}

    public static class JobListing {
        private String title;
        private String company;
        private String description;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getCompany() { return company; }
        public void setCompany(String company) { this.company = company; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
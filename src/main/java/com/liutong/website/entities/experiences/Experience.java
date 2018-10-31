package com.liutong.website.entities.experiences;

import org.springframework.data.annotation.Id;

public class Experience {
    @Id private String id;
    private Company company;
    private String title;
    private String jobDescription; // TODO: Think about how to represent this later: maybe a list?
    private String techUsed; // TODO: Think about how to represent this later: maybe a list?

    public void setId(String id) { this.id = id; }

    public String getId() { return id; }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getTechUsed() {
        return techUsed;
    }

    public void setTechUsed(String techUsed) {
        this.techUsed = techUsed;
    }
}

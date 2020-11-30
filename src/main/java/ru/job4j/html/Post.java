package ru.job4j.html;

import java.time.LocalDate;

public class Post {
    private LocalDate creationTime;
    private String url;
    private String title;
    private String description;

    public Post() {
    }

    public Post(LocalDate creationTime, String url, String title, String description) {
        this.creationTime = creationTime;
        this.url = url;
        this.title = title;
        this.description = description;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
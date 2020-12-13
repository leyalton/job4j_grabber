package ru.job4j.grabber;

import java.time.LocalDate;

public class Post {
    private LocalDate creationTime;
    private String url;
    private String title;
    private String author;
    private String description;

    public Post() {

    }

    public Post(LocalDate creationTime, String url, String title, String author,
                String description) {
        this.creationTime = creationTime;
        this.url = url;
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDate creationTime) {
        this.creationTime = creationTime;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Post {" + System.lineSeparator()
                + "author = " + author + System.lineSeparator()
                + "creationTime = " + creationTime + System.lineSeparator()
                + "title = '" + title +  System.lineSeparator()
                + "description = '" + description  + System.lineSeparator()
                + "url = '" + url + System.lineSeparator()
                + '}';
    }
}
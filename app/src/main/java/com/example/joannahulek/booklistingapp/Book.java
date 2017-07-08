package com.example.joannahulek.booklistingapp;

        import java.io.Serializable;

/**
 * Created by Joasia on 05.07.2017.
 */

public class Book implements Serializable {

    private final String authors;
    private final String title;
    private final String subtitle;
    private final String publisher;
    private final String publishedDate;
    private final String previewLink;

    public Book(String authors, String title, String subtitle, String publisher, String publishedDate, String previewLink) {
        this.authors = authors;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.previewLink = previewLink;
    }

    public String getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getPreviewLink() {
        return previewLink;
    }
}

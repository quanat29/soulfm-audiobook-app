package com.example.soulfm.trend_book;

import java.io.Serializable;

public class TrendBook implements Serializable {
    private String id;
    private String image_url;
    private String title;
    private String authors;

    public TrendBook(String id, String image_url, String title, String authors) {
        this.id = id;
        this.image_url = image_url;
        this.title = title;
        this.authors = authors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
}

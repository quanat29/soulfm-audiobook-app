package com.example.soulfm.book;

import java.io.Serializable;

public class Book implements Serializable {

    private String image_url;
    private String title;
    private String id;
    private String authors;

    public Book(String id, String image_url, String title, String authors) {
        this.image_url = image_url;
        this.title = title;
        this.authors = authors;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
}

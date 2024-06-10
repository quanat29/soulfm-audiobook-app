package com.example.soulfm.detail_book;

public class DetailBook {
    private int Id_book;
    private String title;
    private String authors;
    private String description;
    private int num_comment;
    private String num_star;
    private String category;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNum_comment() {
        return num_comment;
    }

    public void setNum_comment(int num_comment) {
        this.num_comment = num_comment;
    }

    public String getNum_star() {
        return num_star;
    }

    public void setNum_star(String num_star) {
        this.num_star = num_star;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId_book() {
        return Id_book;
    }

    public void setId_book(int id_book) {
        Id_book = id_book;
    }
}

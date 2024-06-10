package com.example.soulfm.category;

import com.example.soulfm.book.Book;

import java.io.Serializable;
import java.util.List;



public class Category implements Serializable {
    private String nameCategory;
    private String viewall;
    private List<Book> books;

    public Category(String nameCategory, String viewall, List<Book> books) {
        this.nameCategory = nameCategory;
        this.viewall = viewall;
        this.books = books;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getViewall() {
        return viewall;
    }

    public void setViewall(String viewall) {
        this.viewall = viewall;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

package com.example.soulfm.favorite_book;

public class FavoriteBook {

    private int Id_favoritebook;
    private int Id_user;
    private int Id_book;
    private String title;
    private String image_url;

    public FavoriteBook(int id_favoritebook, int id_user, int id_book, String title, String image_url) {
        Id_favoritebook = id_favoritebook;
        Id_user = id_user;
        Id_book = id_book;
        this.title = title;
        this.image_url = image_url;
    }

    public int getId_favoritebook() {
        return Id_favoritebook;
    }

    public void setId_favoritebook(int id_favoritebook) {
        Id_favoritebook = id_favoritebook;
    }

    public int getId_user() {
        return Id_user;
    }

    public void setId_user(int id_user) {
        Id_user = id_user;
    }

    public int getId_book() {
        return Id_book;
    }

    public void setId_book(int id_book) {
        Id_book = id_book;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}

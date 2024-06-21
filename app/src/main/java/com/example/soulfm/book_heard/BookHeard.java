package com.example.soulfm.book_heard;

public class BookHeard {

    private int Id_bookheard;
    private String Currentduration;
    private int Id_user;
    private int Id_bookchapter;
    private int Id_book;
    private String image_url;
    private String title;
    private String Tenchapter;
    private String episode;

    public BookHeard(int id_bookheard, String currentduration, int id_bookchapter, String image_url, String title) {
        Id_bookheard = id_bookheard;
        Currentduration = currentduration;
        Id_bookchapter = id_bookchapter;
        this.image_url = image_url;
        this.title = title;
    }

    public int getId_bookheard() {
        return Id_bookheard;
    }

    public void setId_bookheard(int id_bookheard) {
        Id_bookheard = id_bookheard;
    }

    public String getCurrentduration() {
        return Currentduration;
    }

    public void setCurrentduration(String currentduration) {
        Currentduration = currentduration;
    }

    public int getId_user() {
        return Id_user;
    }

    public void setId_user(int id_user) {
        Id_user = id_user;
    }

    public int getId_bookchapter() {
        return Id_bookchapter;
    }

    public void setId_bookchapter(int id_bookchapter) {
        Id_bookchapter = id_bookchapter;
    }

    public int getId_book() {
        return Id_book;
    }

    public void setId_book(int id_book) {
        Id_book = id_book;
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

    public String getTenchapter() {
        return Tenchapter;
    }

    public void setTenchapter(String tenchapter) {
        Tenchapter = tenchapter;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }
}

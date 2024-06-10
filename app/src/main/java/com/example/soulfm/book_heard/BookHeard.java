package com.example.soulfm.book_heard;

public class BookHeard {

    private int imageSource;
    private String title;
    private int process;

    public BookHeard(int imageSource, String title, int process) {
        this.imageSource = imageSource;
        this.title = title;
        this.process = process;
    }

    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }
}

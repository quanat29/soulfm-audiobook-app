package com.example.soulfm.chapter;

import java.io.Serializable;
import java.sql.Time;

public class Chapter implements Serializable {

    private String Tenchapter;
    private String Audiofile;
    private String Duration;
    private int Id_book;
    private String episode;
    private int num_sections;
    private String Anhbia;
    private String Tensach;

    public Chapter(String tenchapter, String audiofile, String duration, int id_book, String episode, int num_sections, String anhbia, String tensach) {
        Tenchapter = tenchapter;
        Audiofile = audiofile;
        Duration = duration;
        Id_book = id_book;
        this.episode = episode;
        this.num_sections = num_sections;
        Anhbia = anhbia;
        Tensach = tensach;
    }

    public String getTenchapter() {
        return Tenchapter;
    }

    public void setTenchapter(String tenchapter) {
        Tenchapter = tenchapter;
    }

    public String getAudiofile() {
        return Audiofile;
    }

    public void setAudiofile(String audiofile) {
        Audiofile = audiofile;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public int getId_book() {
        return Id_book;
    }

    public void setId_book(int id_book) {
        Id_book = id_book;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public int getNum_sections() {
        return num_sections;
    }

    public void setNum_sections(int num_sections) {
        this.num_sections = num_sections;
    }

    public String getAnhbia() {
        return Anhbia;
    }

    public void setAnhbia(String anhbia) {
        Anhbia = anhbia;
    }

    public String getTensach() {
        return Tensach;
    }

    public void setTensach(String tensach) {
        Tensach = tensach;
    }
}

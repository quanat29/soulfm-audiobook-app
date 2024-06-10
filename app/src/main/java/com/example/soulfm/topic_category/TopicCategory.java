package com.example.soulfm.topic_category;

public class TopicCategory {

    private int Id_category;
    private String Tentheloai;
    private int ImageCategory;

    public TopicCategory(int id_category, String tentheloai, int imageCategory) {
        Id_category = id_category;
        Tentheloai = tentheloai;
        ImageCategory = imageCategory;
    }

    public int getId_category() {
        return Id_category;
    }

    public void setId_category(int id_category) {
        Id_category = id_category;
    }

    public String getTentheloai() {
        return Tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        Tentheloai = tentheloai;
    }

    public int getImageCategory() {
        return ImageCategory;
    }

    public void setImageCategory(int imageCategory) {
        ImageCategory = imageCategory;
    }
}

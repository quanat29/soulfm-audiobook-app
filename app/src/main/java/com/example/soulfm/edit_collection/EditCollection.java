package com.example.soulfm.edit_collection;

public class EditCollection {

    private boolean isChecked;
    private int ImageId;
    private String title;

    public EditCollection(boolean isChecked, int imageId, String title) {
        this.isChecked = isChecked;
        ImageId = imageId;
        this.title = title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

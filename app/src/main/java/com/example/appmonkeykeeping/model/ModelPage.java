package com.example.appmonkeykeeping.model;

public class ModelPage {
    private int image;
    private String title;
    private String desc;
    private int progress;

    public ModelPage(int image, String title, String desc, int progress) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.progress = progress;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}

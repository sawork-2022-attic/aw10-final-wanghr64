package com.example.batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private String main_cat;

    private String title;

    private String asin;

    private List<String> category;

    private List<String> imageURLHighRes;

    public String getMain_cat() {
        return this.main_cat;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAsin() {
        return this.asin;
    }

    public List<String> getCategory() {
        return this.category;
    }

    public List<String> getImageURLHighRes() {
        return imageURLHighRes;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public void setImageURLHighRes(List<String> imageURLHighRes) {
        this.imageURLHighRes = imageURLHighRes;
    }

    public void setMain_cat(String main_cat) {
        this.main_cat = main_cat;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

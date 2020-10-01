package com.emile_project.movie_catalog_service.domain;

public class CatalogItem {
    private String name;
    private String dsc;
    private  int rating;

    public CatalogItem(String name, String dsc, int rating) {
        this.name = name;
        this.dsc = dsc;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

package com.android.qls.model;

import android.net.Uri;

import com.android.qls.R;

public class Book {
    private int id;
    private String name;
    private int idType;
    private String description;
    private String image;
    private String review;

    public Book(int id, String name, int idType, String description, String image, String review) {
        this.id = id;
        this.name = name;
        this.idType = idType;
        this.description = description;
        this.image = image;
        this.review = review;
    }

    public Book(String name, int idType, String description, String image, String review) {
        this.id = id;
        this.name = name;
        this.idType = idType;
        this.description = description;
        this.image = image;
        this.review = review;
    }

    public Book(int id, String name, int idType, String description, int image, String review) {
        this.id = id;
        this.name = name;
        this.idType = idType;
        this.description = description;
        this.image = getURLForResource(image);
        this.review = review;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Uri getImageUri() {
        return Uri.parse(image);
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }
}

package com.android.qls.model;

public class BookType {
    private int id;
    private String name;
    private String describe;

    public BookType(int id, String name, String describe) {
        this.id = id;
        this.name = name;
        this.describe = describe;
    }

    public BookType(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }

    public BookType(int id, String name) {
        this.id = id;
        this.name = name;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}

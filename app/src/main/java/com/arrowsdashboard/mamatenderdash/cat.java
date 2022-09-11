package com.arrowsdashboard.mamatenderdash;

public class cat {
    String  name,photo ;
    int id ;

    public cat() {
    }

    public cat(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public cat(String name, String photo, int id) {
        this.name = name;
        this.photo = photo;
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

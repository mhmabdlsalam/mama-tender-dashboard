package com.arrowsdashboard.mamatenderdash;

public class drink {
    String name,img ;
    int id ;
    boolean is_need ;
    public drink() {
    }

    public drink(String name, String img, int id, boolean is_need) {
        this.name = name;
        this.img = img;
        this.id = id;
        this.is_need = is_need;
    }

    public boolean isIs_need() {
        return is_need;
    }

    public void setIs_need(boolean is_need) {
        this.is_need = is_need;
    }

    public drink(String name, String img, int id) {
        this.name = name;
        this.img = img;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public drink(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

package com.arrowsdashboard.mamatenderdash;

public class branch {
    String url  ;
    int id ;

    public branch(String url, int id) {
        this.url = url;
        this.id = id;
    }

    public branch() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

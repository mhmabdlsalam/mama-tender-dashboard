package com.arrowsdashboard.mamatenderdash;

public class banner {
    String url  ;
    int id ;

    public banner(String url, int id) {
        this.url = url;
        this.id = id;
    }

    public banner() {
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

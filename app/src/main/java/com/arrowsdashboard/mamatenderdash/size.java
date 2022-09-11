package com.arrowsdashboard.mamatenderdash;

public class size {
    String size , price ;
    Boolean is_need;

    public size() {
    }
    public size(String size, String price) {
        this.size = size;
        this.price = price;
    }

    public size(String size, String price, Boolean is_need) {
        this.size = size;
        this.price = price;
        this.is_need = is_need;
    }

    public Boolean getIs_need() {
        return is_need;
    }

    public void setIs_need(Boolean is_need) {
        this.is_need = is_need;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

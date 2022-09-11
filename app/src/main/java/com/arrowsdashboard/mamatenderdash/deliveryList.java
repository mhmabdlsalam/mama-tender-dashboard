package com.arrowsdashboard.mamatenderdash;

public class deliveryList {
    String area , id , price ;

    public deliveryList() {
    }

    public deliveryList(String area, String id, String price) {
        this.area = area;
        this.id = id;
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return this.area; // What to display in the Spinner list.
    }
}

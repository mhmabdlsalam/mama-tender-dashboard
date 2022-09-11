package com.arrowsdashboard.mamatenderdash;

import android.text.TextUtils;

public class DeliveryModel {
    private String id, price, area;

    public DeliveryModel() {
    }

    public DeliveryModel(String id, String price, String area) {
        this.id = id;
        this.price = price;
        this.area = area;
    }

    public DeliveryModel(String price, String area) {
        this.price = price;
        this.area = area;
    }

    public String getId() {
        return id;
    }


    public String getPrice() {
        if (TextUtils.isEmpty(price)) {
            return "0";
        } else {
            return price;
        }
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        if (TextUtils.isEmpty(area)) {
            return " اكتب المنطقة ";
        } else {
            return area;
        }
    }

    public void setArea(String area) {
        this.area = area;
    }
}

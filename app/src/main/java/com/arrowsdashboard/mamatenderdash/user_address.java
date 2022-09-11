package com.arrowsdashboard.mamatenderdash;

public class user_address {
    String address ,lat ,lng ,build_number ,floor_number ,landmark ;
    Boolean checked ;
    deliveryList user_area ;

    public user_address() {
    }

    public deliveryList getUser_area() {
        return user_area;
    }

    public void setUser_area(deliveryList user_area) {
        this.user_area = user_area;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public user_address(String address, String lat, String lng, String build_number, String floor_number, String landmark,deliveryList user_area) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.build_number = build_number;
        this.floor_number = floor_number;
        this.landmark = landmark;
        this.user_area = user_area;
    }

    public user_address(String address, String lat, String lng) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public String getBuild_number() {
        return build_number;
    }

    public void setBuild_number(String build_number) {
        this.build_number = build_number;
    }

    public String getFloor_number() {
        return floor_number;
    }

    public void setFloor_number(String floor_number) {
        this.floor_number = floor_number;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}

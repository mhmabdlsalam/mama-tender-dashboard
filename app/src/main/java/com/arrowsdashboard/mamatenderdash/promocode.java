package com.arrowsdashboard.mamatenderdash;

public class promocode {
    String promo,value,type ;
    int id ;

    public promocode(String promo, String value, String type, int id) {
        this.promo = promo;
        this.value = value;
        this.type = type;
        this.id = id;
    }

    public promocode() {
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

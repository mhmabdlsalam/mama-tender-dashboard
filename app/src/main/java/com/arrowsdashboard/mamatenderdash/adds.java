package com.arrowsdashboard.mamatenderdash;

public class adds {
    String adds , price  ;
    boolean is_need ;

    public adds(String adds, String price) {
        this.adds = adds;
        this.price = price;
    }

    public adds(String adds, String price, boolean is_need) {
        this.adds = adds;
        this.price = price;
        this.is_need = is_need;
    }

    public boolean isIs_need() {
        return is_need;
    }

    public void setIs_need(boolean is_need) {
        this.is_need = is_need;
    }

    public adds() {
    }

    public String getAdds() {
        return adds;
    }

    public void setAdds(String adds) {
        this.adds = adds;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

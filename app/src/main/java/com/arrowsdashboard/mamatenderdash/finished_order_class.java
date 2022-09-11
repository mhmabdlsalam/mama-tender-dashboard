package com.arrowsdashboard.mamatenderdash;

import java.util.List;

public class finished_order_class {
    String orderid , total_price ;
    List<product> list_of_product ;

    public finished_order_class(String orderid, String total_price) {
        this.orderid = orderid;
        this.total_price = total_price;

    }

    public finished_order_class() {
    }

    public String getOrderid() {
        return orderid;
    }

    public List<product> getList_of_product() {
        return list_of_product;
    }

    public void setList_of_product(List<product> list_of_product) {
        this.list_of_product = list_of_product;
    }

    public finished_order_class(String orderid, String total_price, List<product> list_of_product) {
        this.orderid = orderid;
        this.total_price = total_price;
        this.list_of_product = list_of_product;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}

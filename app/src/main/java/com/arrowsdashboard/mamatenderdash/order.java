package com.arrowsdashboard.mamatenderdash;

import java.util.List;

public class order {
    List <product> list_of_product ;
    String price ,discount ,delivery,tax,extra,total_price,branch,order_status,order_id,message;
    user_address address ;
    user client ;

    public order(List<product> list_of_product, String price, String discount, String delivery, String tax, String extra, String total_price, String branch, String order_status, String order_id, user_address address, user client) {
        this.list_of_product = list_of_product;
        this.price = price;
        this.discount = discount;
        this.delivery = delivery;
        this.tax = tax;
        this.extra = extra;
        this.total_price = total_price;
        this.branch = branch;
        this.order_status = order_status;
        this.order_id = order_id;
        this.address = address;
        this.client = client;
    }

    public order(List<product> list_of_product, String price, String discount, String delivery, String tax, String extra, String total_price, String branch, String order_status, String order_id, String message, user_address address, user client) {
        this.list_of_product = list_of_product;
        this.price = price;
        this.discount = discount;
        this.delivery = delivery;
        this.tax = tax;
        this.extra = extra;
        this.total_price = total_price;
        this.branch = branch;
        this.order_status = order_status;
        this.order_id = order_id;
        this.message = message;
        this.address = address;
        this.client = client;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public user getClient() {
        return client;
    }

    public void setClient(user client) {
        this.client = client;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public order() {

    }

    public List<product> getList_of_product() {
        return list_of_product;
    }

    public void setList_of_product(List<product> list_of_product) {
        this.list_of_product = list_of_product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public user_address getAddress() {
        return address;
    }

    public void setAddress(user_address address) {
        this.address = address;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}

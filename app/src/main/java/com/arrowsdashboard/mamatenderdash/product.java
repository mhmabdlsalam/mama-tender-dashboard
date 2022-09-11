package com.arrowsdashboard.mamatenderdash;

import java.util.List;

public class product {
    String image,category,name,number,price ;
    List<size> sizes ;
    List<content> content ;
    List<String> conten ;
    List<adds> addsList ;
    List<drink> list_of_drinks ;

    String id,total_price ;


    public product() {
    }

    public List<drink> getList_of_drinks() {
        return list_of_drinks;
    }

    public void setList_of_drinks(List<drink> list_of_drinks) {
        this.list_of_drinks = list_of_drinks;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public product(String image, String category, String name, String number, String price, List<size> sizes, List<com.arrowsdashboard.mamatenderdash.content> content, List<String> conten, List<adds> addsList, List<drink> list_of_drinks, String id, String total_price) {
        this.image = image;
        this.category = category;
        this.name = name;
        this.number = number;
        this.price = price;
        this.sizes = sizes;
        this.content = content;
        this.conten = conten;
        this.addsList = addsList;
        this.list_of_drinks = list_of_drinks;
        this.id = id;
        this.total_price = total_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public product(String image, String category, String name, List<size> sizes, List<String> conten, List<adds> addsList, String id) {
        this.image = image;
        this.category = category;
        this.name = name;
        this.number = number;

        this.sizes = sizes;
        this.conten = conten;
        this.addsList = addsList;
        this.id = id;
    }

    public List<String> getConten() {
        return conten;
    }

    public void setConten(List<String> conten) {
        this.conten = conten;
    }

    public product(String image, String category, String name, List<size> sizes, List<String> conten, List<adds> addsList) {
        this.image = image;
        this.category = category;
        this.name = name;
        this.sizes = sizes;
        this.conten = conten;
        this.addsList = addsList;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<size> getSizes() {
        return sizes;
    }

    public void setSizes(List<size> sizes) {
        this.sizes = sizes;
    }

    public List<content> getContent() {
        return content;
    }

    public void setContent(List<content> content) {
        this.content = content;
    }

    public List<adds> getAddsList() {
        return addsList;
    }

    public void setAddsList(List<adds> addsList) {
        this.addsList = addsList;
    }
}

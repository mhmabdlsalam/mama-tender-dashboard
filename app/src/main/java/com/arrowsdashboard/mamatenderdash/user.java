package com.arrowsdashboard.mamatenderdash;

import java.util.List;

public class user {
    String name , phone , password,device_token ;
    List<user_address> user_address_list ;

    public user() {

    }

    public user(String name, String phone, String password, String device_token, List<user_address> user_address_list) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.device_token = device_token;
        this.user_address_list = user_address_list;
    }

    public user(String name, String phone, String password, String device_token) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.device_token = device_token;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public user(String name, String phone, String password, List<user_address> user_address_list) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.user_address_list = user_address_list;
    }

    public List<user_address> getUser_address_list() {
        return user_address_list;
    }

    public void setUser_address_list(List<user_address> user_address_list) {
        this.user_address_list = user_address_list;
    }

    public user(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

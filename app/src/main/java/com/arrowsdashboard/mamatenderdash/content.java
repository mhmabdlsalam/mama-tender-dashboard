package com.arrowsdashboard.mamatenderdash;

public class content {
    String name ;
    Boolean need ;

    public content() {
    }

    public content(String name, Boolean need) {
        this.name = name;
        this.need = need;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getNeed() {
        return need;
    }

    public void setNeed(Boolean need) {
        this.need = need;
    }
}

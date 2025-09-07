package com.example.refactoring.chapter12.subclassWithDelegate10;

public class Show {

    private String property;
    private int price;

    public boolean hasOwnProperty(String property) {
        return this.property.equals(property);
    }

    public int getPrice() {
        return price;
    }

    public String getProperty() {
        return property;
    }
}

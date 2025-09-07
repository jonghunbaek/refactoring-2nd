package com.example.refactoring.chapter12.subclassWithDelegate10;

public class Extras {

    private String property;
    private int premiumFee;

    public int getPremiumFee() {
        return premiumFee;
    }

    public boolean hasOwnProperty(String dinner) {
        return this.property.equals(dinner);
    }
}

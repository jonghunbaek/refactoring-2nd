package com.example.refactoring.chapter07.replacetempwithquery4;

public class Order {
    private int quantity;
    private Item item;

    public Order(int quantity, Item item) {
        this.quantity = quantity;
        this.item = item;
    }

    public double getPrice() {
        return getBasePrice() * getDiscountFactor();
    }

    private double getDiscountFactor() {
        double discountFactor = 0.98;
        if (getBasePrice() > 1000) {
            discountFactor -= 0.03;
        }

        return discountFactor;
    }

    private int getBasePrice() {
        return this.quantity * this.item.getPrice();
    }
}

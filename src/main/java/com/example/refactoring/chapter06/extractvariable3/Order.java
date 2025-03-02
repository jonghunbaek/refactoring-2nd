package com.example.refactoring.chapter06.extractvariable3;

public class Order {

    private int quantity;
    private int itemPrice;

    public int getQuantity() {
        return quantity;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public int price() {
        return (int) (getBasePrice() - getQuantityDiscount() + getShipping());
    }

    private int getBasePrice() {
        return this.quantity * this.itemPrice;
    }

    private double getQuantityDiscount() {
        return Math.max(0, this.quantity - 500) * this.itemPrice * 0.05;
    }

    private double getShipping() {
        return Math.min(getBasePrice() * 0.1, 100);
    }
}

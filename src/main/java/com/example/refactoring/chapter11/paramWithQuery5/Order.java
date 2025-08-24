package com.example.refactoring.chapter11.paramWithQuery5;

public class Order {

    private int quantity;
    private int price;

    public double getFinalPrice() {
        int basePrice = this.quantity * this.price;
        int discountLevel;
        if (this.quantity > 100) {
            discountLevel = 2;
        } else {
            discountLevel = 1;
        }

        return discountedPrice(basePrice, discountLevel);
    }

    private double discountedPrice(int basePrice, int discountLevel) {
        return switch (discountLevel) {
            case 1 -> basePrice * 0.95;
            case 2 -> basePrice * 0.9;
            default -> 0;
        };
    }
}

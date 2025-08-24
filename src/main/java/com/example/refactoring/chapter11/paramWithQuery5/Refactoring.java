package com.example.refactoring.chapter11.paramWithQuery5;

public class Refactoring {

    private int quantity;
    private int price;

    public double getFinalPrice() {
        int basePrice = this.quantity * this.price;
        return discountedPrice(basePrice);
    }

    private int getDiscountLevel() {
        if (this.quantity > 100) {
            return 2;
        } else {
            return 1;
        }
    }

    private double discountedPrice(int basePrice) {
        return switch (getDiscountLevel()) {
            case 1 -> basePrice * 0.95;
            case 2 -> basePrice * 0.9;
            default -> 0;
        };
    }
}

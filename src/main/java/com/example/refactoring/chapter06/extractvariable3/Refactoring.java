package com.example.refactoring.chapter06.extractvariable3;

public class Refactoring {

    public int price(Order order) {
        int basePrice = order.getQuantity() * order.getItemPrice();
        double quantityDiscount = Math.max(0, order.getQuantity() - 500) * order.getItemPrice() * 0.05;
        double shipping = Math.min(basePrice * 0.1, 100);
        return (int) (basePrice - quantityDiscount + shipping);
    }
}

package com.example.refactoring.chapter06.extractvariable3;

public class Sample {

    public int price(Order order) {
        return (int) (order.getQuantity() * order.getItemPrice() -
                        Math.max(0, order.getQuantity() - 500) * order.getItemPrice() * 0.05 +
                        Math.min(order.getQuantity() * order.getItemPrice() * 0.1, 100));
    }
}

package com.example.refactoring.chapter11.removeFlag3;

import java.time.LocalDate;

public class Order {
    private String deliveryState; // 배송 지역 (e.g., "MA", "CT")
    private LocalDate placedOn;   // 주문 날짜

    public Order(String deliveryState, LocalDate placedOn) {
        this.deliveryState = deliveryState;
        this.placedOn = placedOn;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public LocalDate getPlacedOn() {
        return placedOn;
    }
}

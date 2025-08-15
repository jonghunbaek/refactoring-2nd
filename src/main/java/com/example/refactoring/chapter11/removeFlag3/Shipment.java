package com.example.refactoring.chapter11.removeFlag3;

import java.time.LocalDate;

public class Shipment {
    private LocalDate deliveryDate; // 배송 도착일

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "Shipment{deliveryDate=" + deliveryDate + "}";
    }
}

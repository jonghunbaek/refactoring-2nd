package com.example.refactoring.chapter11.removeFlag3;

import java.time.LocalDate;
import java.util.Arrays;

public class Sample {

    public LocalDate deliveryDate(Order anOrder, boolean isRush) {
        if (isRush) {
            int deliveryTime;
            if (Arrays.asList("MA", "CT").contains(anOrder.getDeliveryState())) {
                deliveryTime = 1;
            } else if (Arrays.asList("NY", "NH").contains(anOrder.getDeliveryState())) {
                deliveryTime = 2;
            } else {
                deliveryTime = 3;
            }
            // JavaScript: anOrder.placedOn.plusDays(1 + deliveryTime)
            return anOrder.getPlacedOn().plusDays(1 + deliveryTime);
        } else { // isRush가 false인 경우
            int deliveryTime;
            if (Arrays.asList("MA", "CT", "NY").contains(anOrder.getDeliveryState())) {
                deliveryTime = 2;
            } else if (Arrays.asList("ME", "NH").contains(anOrder.getDeliveryState())) {
                deliveryTime = 4;
            } else {
                deliveryTime = 3;
            }
            return anOrder.getPlacedOn().plusDays(2 + deliveryTime);
        }
    }
}

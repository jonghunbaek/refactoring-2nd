package com.example.refactoring.chapter07.replaceprimitivewithobject3;

import java.util.List;

public class ClientSample {

    public static void main(String[] args) {
        // 리팩터링 전
        OrderSample order1 = new OrderSample("high");
        OrderSample order2 = new OrderSample("rush");

        List<OrderSample> orders = List.of(order1, order2);
        long count = orders.stream()
                .filter(o -> "high".equals(o.priority) || "rush".equals(o.priority))
                .count();
    }
}

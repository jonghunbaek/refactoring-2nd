package com.example.refactoring.chapter07.replaceprimitivewithobject3;

import java.util.List;

public class ClientRefactoring {

    public static void main(String[] args) {
        // 리팩터링 전
        OrderRefactoring order1 = new OrderRefactoring("high");
        OrderRefactoring order2 = new OrderRefactoring("rush");

        List<OrderRefactoring> orders = List.of(order1, order2);
        long count = orders.stream()
                .filter(o -> "high".equals(o.getPriorityString()) || "rush".equals(o.getPriorityString()))
                .count();
    }
}

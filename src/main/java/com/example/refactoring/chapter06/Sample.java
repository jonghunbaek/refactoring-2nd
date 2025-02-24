package com.example.refactoring.chapter06;

import java.time.LocalDate;

public class Sample {

    public void printOwing(Invoice invoice) {
        int outstanding = 0;

        System.out.println("****************");
        System.out.println("*** 고객 채무 ***");
        System.out.println("****************");

        // 미해결 채무를 계산한다.
        for (Order order : invoice.getOrders()) {
            outstanding += order.getAmount();
        }

        // 마감일을 기록한다.
        LocalDate today = Clock.today();
        invoice.setDueDate(today.plusDays(30));

        // 세부 사항 출력
        System.out.println("고객명: " + invoice.getCustomer());
        System.out.println("채무액: " + outstanding);
        System.out.println("마감일: " + invoice.getDueDate());
    }
}

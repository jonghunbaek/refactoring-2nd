package com.example.refactoring.chapter06.extractfunction1;

import java.time.LocalDate;
import java.util.List;

public class Refactoring {

    public void printOwing(Invoice invoice) {

        printBanner();

        // 미해결 채무를 계산한다.
        int outstanding = calculateOutstanding(invoice.getOrders());

        // 마감일을 기록한다.
        LocalDate today = Clock.today();
        invoice.setDueDate(today.plusDays(30));

        // 세부 사항 출력
        printDetails(invoice, outstanding);
    }

    private void printBanner() {
        System.out.println("****************");
        System.out.println("*** 고객 채무 ***");
        System.out.println("****************");
    }

    private int calculateOutstanding(List<Order> orders) {
        return orders.stream()
                .mapToInt(Order::getAmount)
                .sum();
    }

    private void printDetails(Invoice invoice, int outstanding) {
        System.out.println("고객명: " + invoice.getCustomer());
        System.out.println("채무액: " + outstanding);
        System.out.println("마감일: " + invoice.getDueDate());
    }
}

package com.example.refactoring.chapter06;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

    private String customer;
    private LocalDate dueDate;
    private List<Order> orders = new ArrayList<>();

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String getCustomer() {
        return this.customer;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }
}

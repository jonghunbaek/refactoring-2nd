package com.example.refactoring.chapter10.specialCase5;

public class Site {

    private Customer customer;

    public Customer getCustomer() {
        return customer.getName().equals("미확인 고객") ? new UnknownCustomer() : customer;
    }
}

package com.example.refactoring.chapter10.specialCase5;

public class Refactoring {

    private Customer customer;

    public void client1() {
        // ... some code

        String customerName = "";
        if (isUnknown(customer)) {
            customerName = "거주자";
        } else {
            customerName = customer.getName();
        }
    }

    public void client2() {
        if (isUnknown(customer)) {
            // something
        }
    }

    public void client3() {
        if (isUnknown(customer)) {
            // something
        }
    }

    public boolean isUnknown(Customer customer) {
        return customer.isUnknown();
    }
}

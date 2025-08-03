package com.example.refactoring.chapter10.specialCase5;

public class Client {

    private Customer customer;

    public Client(Customer customer) {
        this.customer = customer;
    }

    public void client1() {
        // ... some code

        String customerName = "";
        if (customer.getName().equals("미확인 고객")) {
            customerName = "거주자";
        } else {
            customerName = customer.getName();
        }
    }

    public void client2() {
        if (customer.getName().equals("미확인 고객")) {
            // something
        }
    }

    public void client3() {
        if (!customer.getName().equals("미확인 고객")) {
            // something
        }
    }

}

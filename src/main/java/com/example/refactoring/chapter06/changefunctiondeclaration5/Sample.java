package com.example.refactoring.chapter06.changefunctiondeclaration5;

import java.util.Arrays;
import java.util.List;

public class Sample {

    public boolean inNewEngland(Customer customer) {
        return Arrays.stream(new String[] {"MA", "CT", "ME", "VT", "NH", "RI"})
                .anyMatch(val -> val.equalsIgnoreCase(customer.getAddress().getState()));
    }

    public List<Customer> sample(List<Customer> customers) {
        return customers.stream()
                .filter(this::inNewEngland)
                .toList();
    }
}

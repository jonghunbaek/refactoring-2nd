package com.example.refactoring.chapter06.changefunctiondeclaration5;

import java.util.Arrays;
import java.util.List;

public class Refactoring {

    private boolean inNewEngland(String stateCode) {
        return Arrays.stream(new String[]{"MA", "CT", "ME", "VT", "NH", "RI"})
                .anyMatch(val -> val.equalsIgnoreCase(stateCode));
    }

    public List<Customer> sample(List<Customer> customers) {
        return customers.stream()
                .filter(customer -> inNewEngland(customer.getAddress().getState()))
                .toList();
    }
}

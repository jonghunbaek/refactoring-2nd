package com.example.refactoring.chapter10.specialCase5;

public class UnknownCustomer extends Customer {

    @Override
    public boolean isUnknown() {
        return true;
    }

    public String getName() {
        return "거주자";
    }
}

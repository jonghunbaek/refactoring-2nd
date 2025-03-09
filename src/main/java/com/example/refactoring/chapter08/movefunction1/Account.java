package com.example.refactoring.chapter08.movefunction1;

public class Account {

    private AccountType type;
    private double daysOverdrawn;

    public double getBankCharge() {
        double result = 4.5;
        if (daysOverdrawn > 0) {
            result += type.getOverdraftCharge(daysOverdrawn);
        }

        return result;
    }
}

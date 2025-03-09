package com.example.refactoring.chapter08.movefunction1;

public class AccountType {

    private String type;

    public boolean isPremium() {
        return true;
    }

    public double getOverdraftCharge(double daysOverdrawn) {
        if (isPremium()) {
            int baseCharge = 10;
            if (daysOverdrawn <= 7) {
                return baseCharge;
            } else {
                return baseCharge + (daysOverdrawn - 7) * 0.85;
            }
        } else {
            return daysOverdrawn * 1.75;
        }
    }
}

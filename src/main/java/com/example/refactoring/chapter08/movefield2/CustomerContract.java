package com.example.refactoring.chapter08.movefield2;

import java.time.LocalDate;

public class CustomerContract {

    private LocalDate localDate;
    private double discountRate;

    public CustomerContract() {

    }

    public CustomerContract(LocalDate localDate, double discountRate) {
        this.localDate = localDate;
        this.discountRate = discountRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
}

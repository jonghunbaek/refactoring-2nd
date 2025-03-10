package com.example.refactoring.chapter08.movefield2;

import java.math.BigDecimal;

public class Customer {

    private String name;
    private CustomerContract contract;

    public Customer(String name, double discountRate) {
        this.name = name;
        this.contract = new CustomerContract();
        setDiscountRate(discountRate);
    }

    public double getDiscountRate() {
        return contract.getDiscountRate();
    }

    private void setDiscountRate(double discountRate) {
        contract.setDiscountRate(discountRate);
    }

    public void becomePreferred() {
        setDiscountRate(getDiscountRate() + 0.03);

        // ...
    }

    public BigDecimal applyDiscount(BigDecimal amount) {
        return amount.subtract(amount.multiply(BigDecimal.valueOf(getDiscountRate())));
    }
}

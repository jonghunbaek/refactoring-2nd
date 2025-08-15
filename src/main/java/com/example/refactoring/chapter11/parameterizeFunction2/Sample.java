package com.example.refactoring.chapter11.parameterizeFunction2;

import java.math.BigDecimal;

public class Sample {

    public void tenPercentRaise(Person aPerson) {
        BigDecimal currentSalary = aPerson.getSalary();
        aPerson.setSalary(currentSalary.multiply(new BigDecimal("1.1")));
    }

    public void fivePercentRaise(Person aPerson) {
        BigDecimal currentSalary = aPerson.getSalary();
        aPerson.setSalary(currentSalary.multiply(new BigDecimal("1.05")));
    }
}

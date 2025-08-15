package com.example.refactoring.chapter11.parameterizeFunction2;

import java.math.BigDecimal;

public class Refactoring {

    public void raise(Person person, double factor) {
        person.setSalary(person.getSalary().multiply(BigDecimal.valueOf(factor)));
    }
}

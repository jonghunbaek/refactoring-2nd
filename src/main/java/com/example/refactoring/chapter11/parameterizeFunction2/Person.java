package com.example.refactoring.chapter11.parameterizeFunction2;

import java.math.BigDecimal;

public class Person {
    private BigDecimal salary;

    public Person(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Person{salary=" + salary + "}";
    }
}

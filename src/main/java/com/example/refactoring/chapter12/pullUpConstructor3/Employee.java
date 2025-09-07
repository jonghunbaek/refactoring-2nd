package com.example.refactoring.chapter12.pullUpConstructor3;

public class Employee extends Party {

    private String name;
    private String id;
    private int monthlyCost;

    public Employee(String name, String id, int monthlyCost) {
        super(name);
        this.id = id;
        this.monthlyCost = monthlyCost;
    }
}

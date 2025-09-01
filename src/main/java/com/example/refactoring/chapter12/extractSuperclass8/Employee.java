package com.example.refactoring.chapter12.extractSuperclass8;

public class Employee extends Party{

    private String id;
    private int monthlyCost;

    public Employee(String name, String id, int monthlyCost) {
        super(name);
        this.id = id;
        this.monthlyCost = monthlyCost;
    }

    public String getId() {
        return id;
    }

    @Override
    public int getMonthlyCost() {
        return monthlyCost;
    }
}

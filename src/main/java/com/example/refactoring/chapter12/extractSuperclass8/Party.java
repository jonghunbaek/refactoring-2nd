package com.example.refactoring.chapter12.extractSuperclass8;

public abstract class Party {

    private String name;

    public Party(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAnnualCost() {
        return this.getMonthlyCost();
    }

    abstract int getMonthlyCost();
}

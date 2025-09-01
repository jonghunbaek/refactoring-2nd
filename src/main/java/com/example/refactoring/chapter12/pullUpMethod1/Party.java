package com.example.refactoring.chapter12.pullUpMethod1;

public abstract class Party {

    public int annualCost(){
        return monthlyCost() * 12;
    }

    abstract int monthlyCost();
}

package com.example.refactoring.chapter09.replacederived3;

import java.util.List;

public class ProductionPlan {

    private int production;
    private List<Adjustment> adjustments;

    public int getProduction() {
        return production;
    }

    public void applyAdjustment(Adjustment adjustment) {
        this.adjustments.add(adjustment);
        this.production += adjustment.getAmount();
    }
}

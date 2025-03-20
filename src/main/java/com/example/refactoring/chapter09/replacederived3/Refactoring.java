package com.example.refactoring.chapter09.replacederived3;

import java.util.List;

public class Refactoring {

    private List<Adjustment> adjustments;

    public int getProduction() {
        return this.adjustments.stream()
                .mapToInt(Adjustment::getAmount)
                .sum();
    }

    public void applyAdjustment(Adjustment adjustment) {
        this.adjustments.add(adjustment);
    }
}

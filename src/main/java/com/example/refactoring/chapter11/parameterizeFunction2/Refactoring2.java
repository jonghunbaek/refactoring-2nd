package com.example.refactoring.chapter11.parameterizeFunction2;

public class Refactoring2 {

    public double baseCharge(int usage) {
        if (usage < 0) {
            return 0;
        }

        return withinBand(usage, 0, 100) * 0.03 + withinBand(usage, 100, 200) * 0.05 + withinBand(usage, 200, Integer.MAX_VALUE) * 0.07;
    }

    private int withinBand(int usage, int bottom, int top) {
        return usage > bottom ? Math.min(usage, top) - bottom : 0;
    }
}

package com.example.refactoring.chapter11.parameterizeFunction2;

public class Sample2 {

    public double baseCharge(int usage) {
        if (usage < 0) {
            return 0;
        }

        return bottomBand(usage) * 0.03 + middleBand(usage) * 0.05 + topBand(usage) * 0.07;
    }

    private double topBand(int usage) {
        return Math.min(usage, 100);
    }

    private double middleBand(int usage) {
        return usage > 100 ? Math.min(usage, 200) - 100 : 0;
    }

    private double bottomBand(int usage) {
        return usage > 200 ? usage - 200 : 0;
    }
}

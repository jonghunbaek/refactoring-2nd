package com.example.refactoring.chapter01.tobe;

public class ComedyCalculator extends PerformanceCalculator {

    public ComedyCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    @Override
    public int amountFor() {
        int result = 30000;

        if (performance.getAudience() > 20) {
            result += 10000 + 500 * (performance.getAudience() - 20);
        }

        result += 300 * performance.getAudience();

        return result;
    }

    @Override
    public int volumeCreditFor() {
        int result = Math.max(performance.getAudience() - 30, 0);
        result += (int) Math.floor((double) performance.getAudience() / 5);

        return result;
    }
}

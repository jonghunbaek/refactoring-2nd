package com.example.refactoring.chapter01.tobe;

public class TragedyCalculator extends PerformanceCalculator {

    public TragedyCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    @Override
    public int amountFor() {
        int result = 40000;

        if (performance.getAudience() > 30) {
            result += 1000 * (performance.getAudience() - 30);
        }

        return result;
    }
}

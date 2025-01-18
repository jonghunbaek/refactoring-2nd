package com.example.refactoring.chapter01.tobe;

public class PerformanceCalculator {

    protected Performance performance;
    protected Play play;

    public PerformanceCalculator(Performance performance, Play play) {
        this.performance = performance;
        this.play = play;
    }

    public int amountFor() {
        return 0;
    }

    public int volumeCreditFor() {
        return Math.max(performance.getAudience() - 30, 0);
    }
}

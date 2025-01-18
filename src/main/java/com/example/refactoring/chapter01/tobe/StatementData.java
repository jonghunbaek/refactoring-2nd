package com.example.refactoring.chapter01.tobe;

import java.util.List;

public class StatementData {

    private Invoice invoice;
    private Plays plays;

    public StatementData(Invoice invoice, Plays plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String getCustomer() {
        return invoice.getCustomer();
    }

    public List<Performance> getPerformances() {
        return invoice.getPerformances();
    }

    public Play playFor(Performance performance) {
        return plays.get(performance);
    }

    public int amountFor(Performance performance) {
        return new PerformanceCalculator(performance, playFor(performance))
                .amountFor();
    }

    public int totalAmount() {
        return invoice.getPerformances().stream()
                .mapToInt(this::amountFor)
                .sum();
    }

    public int totalVolumeCredits() {
        return invoice.getPerformances().stream()
                .mapToInt(this::volumeCreditFor)
                .sum();
    }

    private int volumeCreditFor(Performance performance) {
        int result = Math.max(performance.getAudience() - 30, 0);

        if (playFor(performance).getType().equals("comedy")) {
            result += (int) Math.floor((double) performance.getAudience() / 5);
        }

        return result;
    }
}

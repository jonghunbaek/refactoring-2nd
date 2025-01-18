package com.example.refactoring.chapter01.tobe;

import java.util.List;

import static com.example.refactoring.chapter01.tobe.PerformanceCalculatorFactory.*;

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
        return create(performance, playFor(performance))
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
        return create(performance, playFor(performance))
                .volumeCreditFor();
    }
}

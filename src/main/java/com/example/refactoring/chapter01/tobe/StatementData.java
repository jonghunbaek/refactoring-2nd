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

    public Invoice getInvoice() {
        return invoice;
    }

    public Play playFor(Performance performance) {
        return plays.get(performance);
    }

    public int amountFor(Performance performance) {
        int result;
        switch (playFor(performance).getType()) {
            case "tragedy":
                result = 40000;
                if (performance.getAudience() > 30) {
                    result += 1000 * (performance.getAudience() - 30);
                }
                break;
            case "comedy":
                result = 30000;
                if (performance.getAudience() > 20) {
                    result += 10000 + 500 * (performance.getAudience() - 20);
                }
                result += 300 * performance.getAudience();
                break;
            default:
                throw new RuntimeException("알 수 없는 장르");
        }

        return result / 100;
    }

    public int totalAmount(Invoice invoice) {
        int result = 0;

        for (Performance performance : invoice.getPerformances()) {
            result += amountFor(performance);
        }

        return result;
    }

    public int totalVolumeCredits(Invoice invoice) {
        int volumeCredit = 0;

        for (Performance performance : invoice.getPerformances()) {
            volumeCredit += volumeCreditFor(performance);
        }

        return volumeCredit;
    }

    private int volumeCreditFor(Performance performance) {
        int result = Math.max(performance.getAudience() - 30, 0);

        if (playFor(performance).getType().equals("comedy")) {
            result += (int) Math.floor((double) performance.getAudience() / 5);
        }

        return result;
    }
}

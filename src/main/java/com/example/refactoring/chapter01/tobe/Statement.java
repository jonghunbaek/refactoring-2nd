package com.example.refactoring.chapter01.tobe;

public class Statement {
    public String statement(Invoice invoice, Plays plays) {
        StatementData data = new StatementData(invoice, plays);

        return renderPlainText(data, plays);
    }

    private String renderPlainText(StatementData data, Plays plays) {
        StringBuilder result = new StringBuilder(String.format("청구내역 (고객명: %s)\n", data.getCustomer()));
        for (Performance performance : data.getPerformances()) {
            // 청구 내역을 출력한다.
            result.append(String.format("%s: $%d %d석\n",playFor(plays, performance).getName(), amountFor(performance, plays) / 100, performance.getAudience()));
        }

        result.append(String.format("총액: $%d\n", totalAmount(data.getInvoice(), plays) / 100));
        result.append(String.format("적립 포인트: %d점", totalVolumeCredits(data.getInvoice(), plays)));
        return result.toString();
    }

    private int totalAmount(Invoice invoice, Plays plays) {
        int totalAmount = 0;

        for (Performance performance : invoice.getPerformances()) {
            totalAmount += amountFor(performance, plays);
        }

        return totalAmount;
    }

    private int totalVolumeCredits(Invoice invoice, Plays plays) {
        int volumeCredit = 0;

        for (Performance performance : invoice.getPerformances()) {
            volumeCredit += volumeCreditFor(plays, performance);
        }

        return volumeCredit;
    }

    private int volumeCreditFor(Plays plays, Performance performance) {
        int result = Math.max(performance.getAudience() - 30, 0);

        if (playFor(plays, performance).getType().equals("comedy")) {
            result += (int) Math.floor((double) performance.getAudience() / 5);
        }

        return result;
    }

    private int amountFor(Performance performance, Plays plays) {
        int result;
        switch (playFor(plays, performance).getType()) {
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
        return result;
    }

    private Play playFor(Plays plays, Performance performance) {
        return plays.get(performance);
    }

    public String htmlStatement(Invoice invoice, Plays plays) {
        return "<h1>청구내역 (고객명: BigCo)</h1>\n" +
                "<table>\n" +
                "<tr><th>연극</th><th>좌석수</th><th>금액</th></tr>\n" +
                "<tr><td>Hamlet</td><td>55석</td><td>$650.00</td></tr>\n" +
                "<tr><td>As You Like It</td><td>35석</td><td>$580.00</td></tr>\n" +
                "<tr><td>Othello</td><td>40석</td><td>$500.00</td></tr>\n" +
                "</table>\n" +
                "<p>총액: <em>$1,730.00</em></p>\n" +
                "<p>적립 포인트: <em>47</em>점</p>\n";
    }
}
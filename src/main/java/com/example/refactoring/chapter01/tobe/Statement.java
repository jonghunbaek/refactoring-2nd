package com.example.refactoring.chapter01.tobe;

public class Statement {
    public String statement(Invoice invoice, Plays plays) {
        int totalAmount = 0;
        int volumeCredit = 0;
        StringBuilder result = new StringBuilder(String.format("청구내역 (고객명: %s)\n", invoice.getCustomer()));
        for (Performance performance : invoice.getPerformances()) {
            int thisAmount = 0;

            thisAmount = amountFor(performance, plays);

            // 포인트를 적립한다.
            volumeCredit += Math.max(performance.getAudience() - 30, 0);

            // 희극 관객 5명마다 추가 포인트를 제공한다.
            if (playFor(plays, performance).getType().equals("comedy")) {
                volumeCredit += Math.floor(performance.getAudience() / 5);
            }

            // 청구 내역을 출력한다.
            result.append(String.format("%s: $%d %d석\n",playFor(plays, performance).getName(), thisAmount / 100, performance.getAudience()));
            totalAmount += thisAmount;
        }

        result.append(String.format("총액: $%d\n",totalAmount / 100));
        result.append(String.format("적립 포인트: %d점", volumeCredit));
        return result.toString();
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
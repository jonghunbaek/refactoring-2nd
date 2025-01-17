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
            result.append(String.format("%s: $%d %d석\n", data.playFor(performance).getName(), data.amountFor(performance), performance.getAudience()));
        }

        result.append(String.format("총액: $%d\n", data.totalAmount()));
        result.append(String.format("적립 포인트: %d점", data.totalVolumeCredits(data.getInvoice())));
        return result.toString();
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
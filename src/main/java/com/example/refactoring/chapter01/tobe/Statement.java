package com.example.refactoring.chapter01.tobe;

public class Statement {
    public String statement(Invoice invoice, Plays plays) {
        StatementData data = new StatementData(invoice, plays);

        return renderPlainText(data);
    }

    private String renderPlainText(StatementData data) {
        StringBuilder result = new StringBuilder(String.format("청구내역 (고객명: %s)\n", data.getCustomer()));
        for (Performance performance : data.getPerformances()) {
            // 청구 내역을 출력한다.
            result.append(String.format("%s: $%d %d석\n", data.playFor(performance).getName(), data.amountFor(performance), performance.getAudience()));
        }

        result.append(String.format("총액: $%d\n", data.totalAmount()));
        result.append(String.format("적립 포인트: %d점", data.totalVolumeCredits()));
        return result.toString();
    }

    public String htmlStatement(Invoice invoice, Plays plays) {
        StatementData data = new StatementData(invoice, plays);

        return renderHtmlText(data);
    }

    public String renderHtmlText(StatementData data) {
        StringBuilder result = new StringBuilder();
        result.append(String.format("<h1>청구 내역 (고객명: %s)</h1>\n", data.getCustomer()));

        result.append("<table>\n");
        result.append("<tr><th>연극</th><th>좌석 수</th><th>금액</th></tr>\n");
        for(Performance performance : data.getPerformances()) {
            result.append(String.format("<tr><td>%s</td><td>%d석</td>", data.playFor(performance).getName(), performance.getAudience()));
            result.append(String.format("<td>$%d</td></tr>\n", data.amountFor(performance)));
        }
        result.append("</table>\n");

        result.append(String.format("<p>총액: <em>$%d</em></p>\n", data.totalAmount()));
        result.append(String.format("<p>적립 포인트: <em>%d점</em></p>\n", data.totalVolumeCredits()));
        return result.toString();
    }
}
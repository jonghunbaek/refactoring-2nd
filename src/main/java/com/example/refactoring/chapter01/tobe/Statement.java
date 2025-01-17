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

    public String renderHtmlText(StatementData statementData) {
        StringBuilder result = new StringBuilder(String.format("<h1> 청구내역 (고객명: %s)</h1> \n", statementData.getCustomer()));

        result.append("<table> \n");
        result.append("<tr><th>연극</th><th>좌석수</th><th>금액</th></tr> \n");
        for (Performance performance : statementData.getPerformances()) {
            result.append(String.format("<tr><td>%s</td><td>%d석</td><td>$%d</td></tr>\n",statementData.playFor(performance).getName(), performance.getAudience(), statementData.amountFor(performance)));
        }
        result.append("</table>\n");

        result.append(String.format("<p>총액: <em>$%d</em></p> \n", statementData.totalAmount()));
        result.append(String.format("<p>적립 포인트: <em>%d</em>점</p> \n", statementData.totalVolumeCredits()));
        return result.toString();
    }
}
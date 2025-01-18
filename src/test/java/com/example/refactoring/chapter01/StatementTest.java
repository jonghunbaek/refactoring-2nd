package com.example.refactoring.chapter01;

import com.example.refactoring.JsonReader;
import com.example.refactoring.chapter01.tobe.Invoice;
import com.example.refactoring.chapter01.tobe.Plays;
import com.example.refactoring.chapter01.tobe.Statement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StatementTest {

    private final JsonReader reader = new JsonReader();

    @Test
    @DisplayName("공연 청구서 테스트 - 기본 출력")
    void testStatement1(){
        Invoice invoice = reader.readJsonFile("data/chapter01/invoice.json", Invoice.class);
        Plays plays = reader.readJsonFile("data/chapter01/plays.json", Plays.class);

        Statement statement = new Statement();

        assertThat(statement.statement(invoice, plays)).isEqualTo("청구내역 (고객명: BigCo)\n" +
                "Hamlet: $650 55석\n" +
                "As You Like It: $580 35석\n" +
                "Othello: $500 40석\n" +
                "총액: $1730\n" +
                "적립 포인트: 47점");
    }

    @Test
    @DisplayName("공연 청구서 테스트 - HTML 출력")
    void testStatementHtml(){
        Invoice invoice = reader.readJsonFile("data/chapter01/invoice.json", Invoice.class);
        Plays plays = reader.readJsonFile("data/chapter01/plays.json", Plays.class);

        Statement statement = new Statement();

        assertThat(statement.htmlStatement(invoice, plays)).isEqualTo("<h1>청구 내역 (고객명: BigCo)</h1>\n" +
                "<table>\n" +
                "<tr><th>연극</th><th>좌석 수</th><th>금액</th></tr>\n" +
                "<tr><td>Hamlet</td><td>55석</td><td>$650</td></tr>\n" +
                "<tr><td>As You Like It</td><td>35석</td><td>$580</td></tr>\n" +
                "<tr><td>Othello</td><td>40석</td><td>$500</td></tr>\n" +
                "</table>\n" +
                "<p>총액: <em>$1730</em></p>\n" +
                "<p>적립 포인트: <em>47점</em></p>\n");
    }

    @Test
    @DisplayName("공연 청구서 테스트 - 비극 30명 이하인 경우")
    void testStatementWhenTragedyAudienceUnderThirty() {
        Invoice invoice = reader.readJsonFile("data/chapter01/invoice3.json", Invoice.class);
        Plays plays = reader.readJsonFile("data/chapter01/plays3.json", Plays.class);

        Statement statement = new Statement();

        assertThat(statement.statement(invoice, plays)).isEqualTo("청구내역 (고객명: Chandler)\n" +
                "hamlet: $400 30석\n" +
                "othello: $400 30석\n" +
                "총액: $800\n" +
                "적립 포인트: 0점");
    }

    @Test
    @DisplayName("공연 청구서 테스트 - 희극 20명 이하인 경우")
    void testStatementWhenComedyAudienceUnderTwenty() {
        Invoice invoice = reader.readJsonFile("data/chapter01/invoice2.json", Invoice.class);
        Plays plays = reader.readJsonFile("data/chapter01/plays2.json", Plays.class);

        Statement statement = new Statement();

        assertThat(statement.statement(invoice, plays)).isEqualTo("청구내역 (고객명: Handler)\n" +
                "오징어게임2: $360 20석\n" +
                "나는솔로: $360 20석\n" +
                "총액: $720\n" +
                "적립 포인트: 8점");
    }
}

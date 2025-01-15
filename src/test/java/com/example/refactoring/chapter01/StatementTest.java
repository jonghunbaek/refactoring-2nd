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
    @DisplayName("챕터1 기본 테스트")
    void testStatement1() throws Exception {
        String invoicePath = "data/chapter01/invoice.json";
        String playsPath = "data/chapter01/plays.json";

        Invoice invoice = reader.readJsonFile(invoicePath, Invoice.class);
        Plays plays = reader.readJsonFile(playsPath, Plays.class);

        Statement statement = new Statement();
        String result = statement.statement(invoice, plays);

        System.out.println(result);
        assertThat(result).isEqualTo("청구내역 (고객명: BigCo)\n" +
                "hamlet: $650 55석\n" +
                "As You Like It: $580 35석\n" +
                "Othello: $500 40석\n" +
                "총액: $1730\n" +
                "적립 포인트: 47점");
    }
}

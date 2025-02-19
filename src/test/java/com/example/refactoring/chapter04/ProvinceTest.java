package com.example.refactoring.chapter04;

import com.example.refactoring.JsonReader;
import com.example.refactoring.chapter02.Province;
import com.example.refactoring.chapter02.ProvinceData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProvinceTest {

    private final JsonReader reader = new JsonReader();

    @DisplayName("생산 부족분 계산 결과를 테스트한다.")
    @Test
    void shortfallTest() {
        Province province = new Province(reader.readJsonFile("data/chapter04/data.json", ProvinceData.class));

        assertThat(province.getShortfall()).isEqualTo(5);
    }

    @DisplayName("총 수익 계산 결과를 테스트한다.")
    @Test
    void profitTest() {
        Province province = new Province(reader.readJsonFile("data/chapter04/data.json", ProvinceData.class));

        assertThat(province.getProfit()).isEqualTo(230);
    }
}

package com.example.refactoring.chapter04;

import com.example.refactoring.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProvinceTest {

    private JsonReader reader;
    private ProvinceData provinceData;

    @BeforeEach
    void setUp() {
        reader = new JsonReader();
        provinceData = reader.readJsonFile("data/chapter04/data.json", ProvinceData.class);
    }

    @DisplayName("생산 부족분 계산 결과를 테스트한다.")
    @Test
    void shortfallTest() {
        Province province = new Province(provinceData);

        assertThat(province.getShortfall()).isEqualTo(5);
    }

    @DisplayName("총 수익 계산 결과를 테스트한다.")
    @Test
    void profitTest() {
        Province province = new Province(provinceData);

        assertThat(province.getProfit()).isEqualTo(230);
    }
}

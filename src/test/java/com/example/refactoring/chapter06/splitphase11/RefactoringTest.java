package com.example.refactoring.chapter06.splitphase11;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class RefactoringTest {

    private static final ObjectMapper mapper = new ObjectMapper();
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        // 임시 파일 생성
        tempFile = Files.createTempFile("orders", ".json");

        // 테스트용 JSON 데이터 작성
        String jsonContent = """
            [
                {"status": "ready"},
                {"status": "completed"},
                {"status": "ready"}
            ]
            """;

        // 파일에 JSON 데이터 쓰기
        Files.writeString(tempFile, jsonContent);
    }

    @AfterEach
    void tearDown() throws IOException {
        // 테스트 완료 후 임시 파일 삭제
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testRunWithoutOption() throws IOException {
        // 파일 이름을 매개변수로 전달
        String[] args = {tempFile.toString()};

        // run() 실행 및 결과 검증
        long result = Refactoring.run(args);
        assertEquals(3, result);  // JSON의 모든 주문 개수
    }

    @Test
    void testRunWithReadyFilter() throws IOException {
        // "-r" 옵션 추가
        String[] args = {"-r", tempFile.toString()};

        // run() 실행 및 결과 검증
        long result = Refactoring.run(args);
        assertEquals(2, result);  // "ready" 상태인 주문 개수
    }

    @Test
    void testWhenArgsZero() throws IOException {
        String[] args = {};

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Refactoring.run(args))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("파일명을 입력하세요.");
    }
}

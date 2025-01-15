package com.example.refactoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class JsonReader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T readJsonFile(String filePath, Class<T> clazz) {
        try {
            ClassPathResource resource = new ClassPathResource(filePath);

            return objectMapper.readValue(resource.getInputStream(), clazz);
        } catch (IOException e) {
            throw new RuntimeException("JSON 파싱 실패", e);
        }
    }
}

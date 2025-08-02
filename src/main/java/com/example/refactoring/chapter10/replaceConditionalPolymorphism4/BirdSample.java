package com.example.refactoring.chapter10.replaceConditionalPolymorphism4;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.refactoring.chapter10.replaceConditionalPolymorphism4.BirdFactory.createBird;

public class BirdSample {

    public Map<String, String> plumages(Bird[] birds) {
        return Arrays.stream(birds)
                .collect(Collectors.toMap(
                        bird -> bird.name,
                        bird -> createBird(bird).plumage()
                ));
    }

    // 새 배열을 속도로 매핑
    public Map<String, Integer> speeds(Bird[] birds) {
        return Arrays.stream(birds)
                .collect(Collectors.toMap(
                        bird -> bird.name,
                        bird -> createBird(bird).airSpeedVelocity()
                ));
    }
}

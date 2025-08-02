package com.example.refactoring.chapter10.replaceConditionalPolymorphism4;

public class BirdFactory {

    public static Bird createBird(Bird bird) {
        switch (bird.type) {
            case "유럽 제비":
                return new EuropeanSwallow(bird);
            case "아프리카 제비":
                return new AfricanSwallow(bird);
            case "노르웨이 파랑 앵무":
                return new NorwegianBlueParrot(bird);
            default:
                throw new IllegalArgumentException("일치하는 새가 없음");
        }
    }
}

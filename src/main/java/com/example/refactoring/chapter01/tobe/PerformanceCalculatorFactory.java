package com.example.refactoring.chapter01.tobe;

public class PerformanceCalculatorFactory {

    public static PerformanceCalculator create(Performance performance, Play play) {
        switch (play.getType()) {
            case "tragedy":
                return new TragedyCalculator(performance, play);
            case "comedy":
                return new ComedyCalculator(performance, play);
            default:
                throw new RuntimeException("사용할 수 없는 장르입니다.");
        }
    }
}

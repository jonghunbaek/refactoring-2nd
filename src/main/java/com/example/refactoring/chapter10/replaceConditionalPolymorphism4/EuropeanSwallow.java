package com.example.refactoring.chapter10.replaceConditionalPolymorphism4;

public class EuropeanSwallow extends Bird {
    public EuropeanSwallow(Bird bird) {
        super(bird);
    }

    @Override
    public String plumage() {
        return "보통이다";
    }

    @Override
    public Integer airSpeedVelocity() {
        return 35;
    }
}

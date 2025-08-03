package com.example.refactoring.chapter10.replaceConditionalPolymorphism4;

public class AfricanSwallow extends Bird {
    public AfricanSwallow(Bird bird) {
        super(bird);
    }

    @Override
    public String plumage() {
        return this.numberOfCoconuts > 2 ? "지쳤다" : "보통이다";
    }

    @Override
    public Integer airSpeedVelocity() {
        return 40 - 2 * this.numberOfCoconuts;
    }
}

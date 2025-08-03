package com.example.refactoring.chapter10.replaceConditionalPolymorphism4;

public class NorwegianBlueParrot extends Bird {

    public NorwegianBlueParrot(Bird bird) {
        super(bird);
    }

    @Override
    public String plumage() {
        return this.voltage > 100 ? "그을렸다" : "예쁘다";
    }

    @Override
    public Integer airSpeedVelocity() {
        return this.isNailed ? 0 : 10 + this.voltage / 10;
    }
}

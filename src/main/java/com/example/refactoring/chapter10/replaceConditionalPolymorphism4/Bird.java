package com.example.refactoring.chapter10.replaceConditionalPolymorphism4;

public abstract class Bird {

    String name;
    String type;
    int numberOfCoconuts;
    int voltage;
    boolean isNailed;

    public Bird (Bird bird) {
        this.name = bird.name;

    }

    public abstract String plumage();

    public abstract Integer airSpeedVelocity();
}

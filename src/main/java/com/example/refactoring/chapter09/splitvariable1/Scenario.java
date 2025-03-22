package com.example.refactoring.chapter09.splitvariable1;

public class Scenario {

    private double primaryForce;
    private double secondaryForce;
    private double mass;
    private double delay;

    // 생성자
    public Scenario(double primaryForce, double secondaryForce, double mass, double delay) {
        this.primaryForce = primaryForce;
        this.secondaryForce = secondaryForce;
        this.mass = mass;
        this.delay = delay;
    }

    // Getter 메서드
    public double getPrimaryForce() {
        return primaryForce;
    }

    public double getSecondaryForce() {
        return secondaryForce;
    }

    public double getMass() {
        return mass;
    }

    public double getDelay() {
        return delay;
    }
}

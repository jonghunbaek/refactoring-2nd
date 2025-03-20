package com.example.refactoring.chapter09.splitvariable1;

public class PhysicsCalculator {

    public static double distanceTravelled(Scenario scenario, double time) {
        double result;
        double acc = scenario.getPrimaryForce() / scenario.getMass();
        double primaryTime = Math.min(time, scenario.getDelay());
        result = 0.5 * acc * primaryTime * primaryTime;
        double secondaryTime = time - scenario.getDelay();
        if (secondaryTime > 0) {
            double primaryVelocity = acc * scenario.getDelay();
            acc = (scenario.getPrimaryForce() + scenario.getSecondaryForce()) / scenario.getMass();
            result += primaryVelocity * secondaryTime + 0.5 * acc * secondaryTime * secondaryTime;
        }

        return result;
    }
}

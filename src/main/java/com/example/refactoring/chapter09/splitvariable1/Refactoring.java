package com.example.refactoring.chapter09.splitvariable1;

public class Refactoring {

    public static double distanceTravelled(Scenario scenario, double time) {
        double result;
        final double primaryAcceleration = scenario.getPrimaryForce() / scenario.getMass();
        double primaryTime = Math.min(time, scenario.getDelay());
        result = 0.5 * primaryAcceleration * primaryTime * primaryTime;
        double secondaryTime = time - scenario.getDelay();
        if (secondaryTime > 0) {
            double primaryVelocity = primaryAcceleration * scenario.getDelay();
            final double secondaryAcceleration = (scenario.getPrimaryForce() + scenario.getSecondaryForce()) / scenario.getMass();
            result += primaryVelocity * secondaryTime + 0.5 * secondaryAcceleration * secondaryTime * secondaryTime;
        }

        return result;
    }
}

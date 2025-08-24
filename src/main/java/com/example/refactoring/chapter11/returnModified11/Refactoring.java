package com.example.refactoring.chapter11.returnModified11;

public class Refactoring {

    private Point[] points;
    private int totalTime;
    private int totalDistance;


    public void sample() {
        int value = calculateAscent();
        calculateTime();
        calculateDistance();
    }

    private int calculateAscent() {
        int result = 0;

        for (int i = 1; i < points.length; i++) {
            int verticalChange = points[i].getElevation() - points[i - 1].getElevation();
            result += Math.max(verticalChange, 0);
        }

        return result;
    }

    private void calculateTime() {

    }

    private void calculateDistance() {
    }
}

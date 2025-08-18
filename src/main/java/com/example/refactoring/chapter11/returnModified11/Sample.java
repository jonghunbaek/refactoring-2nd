package com.example.refactoring.chapter11.returnModified11;

public class Sample {

    private Point[] points;
    private int totalAscent;
    private int totalTime;
    private int totalDistance;


    public void sample() {
        calculateAscent();
        calculateTime();
        calculateDistance();
    }

    private void calculateAscent() {
        for (int i = 1; i < points.length; i++) {
            int verticalChange = points[i].getElevation() - points[i - 1].getElevation();
            totalAscent += Math.max(verticalChange, 0);
        }
    }

    private void calculateTime() {

    }

    private void calculateDistance() {
    }
}

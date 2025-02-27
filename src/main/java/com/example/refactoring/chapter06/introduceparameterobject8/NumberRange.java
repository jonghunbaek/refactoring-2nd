package com.example.refactoring.chapter06.introduceparameterobject8;

public class NumberRange {

    private int min;
    private int max;

    public NumberRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}

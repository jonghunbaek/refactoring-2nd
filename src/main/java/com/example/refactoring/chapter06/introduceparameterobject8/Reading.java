package com.example.refactoring.chapter06.introduceparameterobject8;

public class Reading {

    private int temp;
    private String time;

    public Reading(int temp, String time) {
        this.temp = temp;
        this.time = time;
    }

    public int getTemp() {
        return temp;
    }

    public String getTime() {
        return time;
    }
}

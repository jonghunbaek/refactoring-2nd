package com.example.refactoring.chapter06.introduceparameterobject8;

import java.util.List;

public class Station {

    private String name;
    private List<Reading> readings;

    public Station(String name) {
        this.name = name;

        Reading reading1 = new Reading(47, "2016-11-10 09:10");
        Reading reading2 = new Reading(53, "2016-11-10 09:20");
        Reading reading3 = new Reading(58, "2016-11-10 09:30");
        Reading reading4 = new Reading(53, "2016-11-10 09:40");
        Reading reading5 = new Reading(51, "2016-11-10 09:50");

        this.readings = List.of(reading1, reading2, reading3, reading4, reading5);
    }

    public String getName() {
        return name;
    }

    public List<Reading> getReadings() {
        return readings;
    }
}

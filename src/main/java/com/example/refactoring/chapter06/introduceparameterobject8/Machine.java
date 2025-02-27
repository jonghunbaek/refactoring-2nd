package com.example.refactoring.chapter06.introduceparameterobject8;

import java.util.List;

public class Machine {

    public List<Reading> readingsOutsideRange(Station station, int min, int max) {
        return station.getReadings().stream()
                .filter(reading -> reading.getTemp() < min || reading.getTemp() > max)
                .toList();
    }

    public List<Reading> readingsOutsideRangeRefactoring(Station station, NumberRange range) {
        return station.getReadings().stream()
                .filter(reading -> reading.getTemp() < range.getMin() || reading.getTemp() > range.getMax())
                .toList();
    }
}

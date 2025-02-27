package com.example.refactoring.chapter06.introduceparameterobject8;

import java.util.List;

public class Refactoring {

    public static void main(String[] args) {
        Machine machine = new Machine();
        Station station = new Station("ZB1");
        OperationPlan operationPlan = new OperationPlan();
        NumberRange numberRange = new NumberRange(operationPlan.getTemperatureFloor(), operationPlan.getTemperatureCeiling());
        List<Reading> alerts = machine.readingsOutsideRangeRefactoring(station, numberRange);
    }
}

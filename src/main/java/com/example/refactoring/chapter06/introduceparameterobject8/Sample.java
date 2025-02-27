package com.example.refactoring.chapter06.introduceparameterobject8;

import java.util.List;

public class Sample {

    public static void main(String[] args) {
        Machine machine = new Machine();
        Station station = new Station("ZB1");
        OperationPlan operationPlan = new OperationPlan();

        List<Reading> alerts = machine.readingsOutsideRange(station, operationPlan.getTemperatureFloor(), operationPlan.getTemperatureCeiling());

    }
}

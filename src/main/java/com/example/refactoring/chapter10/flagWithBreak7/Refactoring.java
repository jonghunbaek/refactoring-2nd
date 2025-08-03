package com.example.refactoring.chapter10.flagWithBreak7;

import java.util.List;

public class Refactoring {

    public void method(List<String> people ) {
        checkForMiscreants(people);
    }

    private void checkForMiscreants(List<String> people) {
        for (String person : people) {
            if (person.equals("조커")) {
                sendAlert();
                return;
            }

            if (person.equals("사루만")) {
                sendAlert();
                return;
            }
        }
    }

    private void sendAlert() {

    }
}

package com.example.refactoring.chapter10.flagWithBreak7;

import java.util.List;

public class Sample {

    public void method(List<String> people ) {
        boolean found = false;

        for (String person : people) {
            if(!found) {
                if (person.equals("조커")) {
                    sendAlert();
                    found = true;
                }

                if (person.equals("사루만")) {
                    sendAlert();
                    found = true;
                }
            }
        }
    }

    private void sendAlert() {

    }
}

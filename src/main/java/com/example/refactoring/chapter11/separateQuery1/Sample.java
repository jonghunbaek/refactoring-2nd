package com.example.refactoring.chapter11.separateQuery1;

import java.util.List;

public class Sample {

    public String alertForMiscreant(List<String> people ) {
        for (String person : people) {
            if (person.equals("조커")) {
                setOffAlarms();
                return "조커";
            }

            if (person.equals("사루만")) {
                setOffAlarms();
                return "사루만";
            }
        }

        return "";
    }

    private void setOffAlarms() {

    }
}

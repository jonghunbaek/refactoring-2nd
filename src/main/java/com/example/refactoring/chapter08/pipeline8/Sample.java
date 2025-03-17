package com.example.refactoring.chapter08.pipeline8;

import java.util.ArrayList;
import java.util.List;

public class Sample {

    public List<Information> acquireData(String input) {
        String[] lines = input.split("\\n");
        boolean firstLine = true;
        List<Information> result = new ArrayList<>();
        for (String line : lines) {
            if (firstLine) {
                firstLine = false;
                continue;
            }
            if (line.trim().equals("")) continue;
            String[] record = line.split(",");
            if (record[1].trim().equals("India")) {
                result.add(new Information(record[0].trim(), record[2].trim()));
            }
        }

        return result;
    }

    static class Information {
        private String city;
        private String phone;

        public Information(String city, String phone) {
            this.city = city;
            this.phone = phone;
        }
    }
}

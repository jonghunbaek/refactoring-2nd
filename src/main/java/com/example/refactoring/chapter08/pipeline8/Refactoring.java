package com.example.refactoring.chapter08.pipeline8;

import java.util.Arrays;
import java.util.List;

public class Refactoring {

    public List<Information> acquireData(String input) {
        String[] lines = input.split("\\n");
        return Arrays.stream(lines)
                .skip(1)
                .filter(line -> !line.isBlank())
                .map(line -> line.split(","))
                .filter(record -> record[1].trim().equals("India"))
                .map(record -> new Information(record[0].trim(), record[1].trim()))
                .toList();
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

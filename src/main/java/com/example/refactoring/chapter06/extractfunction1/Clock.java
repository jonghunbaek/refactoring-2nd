package com.example.refactoring.chapter06.extractfunction1;

import java.time.LocalDate;

public class Clock {

    public static LocalDate today() {
        return LocalDate.now();
    }
}

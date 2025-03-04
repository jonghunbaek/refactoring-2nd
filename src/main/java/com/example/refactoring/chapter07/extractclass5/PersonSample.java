package com.example.refactoring.chapter07.extractclass5;

public class PersonSample {

    private String name;
    private String officeAreaCode;
    private String officeNumber;

    public String getTelephoneNumber() {
        return "(" + officeAreaCode + ") " + officeNumber;
    }
}

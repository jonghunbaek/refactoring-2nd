package com.example.refactoring.chapter07.extractclass5;

public class PersonRefactoring {

    private String name;
    private TelephoneNumber telephoneNumber;

    public PersonRefactoring(String name, TelephoneNumber telephoneNumber) {
        this.name = name;
        this.telephoneNumber = telephoneNumber;
    }

    public String getTelephoneNumber() {
        return this.telephoneNumber.getTelephoneNumber();
    }

    public String getName() {
        return name;
    }

    public String getOfficeAreaCode() {
        return this.telephoneNumber.getAreaCode();
    }

    public String getOfficeNumber() {
        return this.telephoneNumber.getNumber();
    }
}

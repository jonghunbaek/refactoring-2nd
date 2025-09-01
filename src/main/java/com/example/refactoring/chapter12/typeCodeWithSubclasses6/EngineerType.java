package com.example.refactoring.chapter12.typeCodeWithSubclasses6;

public class EngineerType extends EmployeeType {
    public EngineerType(String name) {
        super(name);
    }

    public String toString() {
        return "engineer";
    }
}

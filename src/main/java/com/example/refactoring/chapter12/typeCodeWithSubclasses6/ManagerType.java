package com.example.refactoring.chapter12.typeCodeWithSubclasses6;

public class ManagerType extends EmployeeType {

    public ManagerType(String name) {
        super(name);
    }

    public String toString() {
        return "manager";
    }
}

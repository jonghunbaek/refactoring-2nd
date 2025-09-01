package com.example.refactoring.chapter12.typeCodeWithSubclasses6;

public class SalespersonType extends EmployeeType {

    public SalespersonType(String value) {
        super(value);
    }

    public String toString() {
        return "salesperson";
    }
}

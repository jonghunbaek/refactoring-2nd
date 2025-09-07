package com.example.refactoring.chapter12.typeCodeWithSubclasses6;

import java.util.Arrays;
import java.util.List;

public class Employee {

    private String name;
    private EmployeeType type;

    public Employee(String name, EmployeeType type) {
        validateType(type.toString());
        this.name = name;
        this.type = type;
    }

    private void validateType(String type) {
        List<String> validTypes = Arrays.asList("engineer", "manager", "salesperson");
        if (!validTypes.contains(type)) {
            throw new IllegalArgumentException("'" + type + "'라는 직원 유형은 없습니다.");
        }
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type.toString();
    }

    public void setType(String type) {
        validateType(type);
        this.type = createEmployeeType(type);
    }

    public String getCapitalizedType() {
        return this.type.toString().substring(0, 1).toUpperCase() + this.type.toString().substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.name, this.getCapitalizedType());
    }

    public static EmployeeType createEmployeeType(String value) {
        switch (value) {
            case "engineer": return new EngineerType("Engineer");
            case "manager": return new ManagerType("Manager");
            case "salesperson": return new SalespersonType("Salesperson");
            default: throw new IllegalArgumentException();
        }
    }
}
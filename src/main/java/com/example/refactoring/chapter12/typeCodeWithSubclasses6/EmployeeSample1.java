package com.example.refactoring.chapter12.typeCodeWithSubclasses6;

public class EmployeeSample1 {

    private String name;

    public EmployeeSample1(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static EmployeeSample1 createEmployee(String name, String type) {
        switch (type) {
            case "engineer": return new Engineer(name);
            case "manager": return new Manager(name);
            case "salesperson": return new Salesperson(name);
        }

        return new EmployeeSample1(name);
    }
}
package com.example.refactoring.chapter12.extractSuperclass8;

import java.util.List;

public class Department extends Party{

    private List<Employee> staff;

    public Department(String name, List<Employee> staff) {
        super(name);
        this.staff = staff;
    }

    public List<Employee> getStaff() {
        return staff;
    }

    @Override
    public int getMonthlyCost() {
        return this.staff.stream()
                .mapToInt(Employee::getMonthlyCost)
                .sum();
    }

    public int getHeadCount() {
        return this.staff.size();
    }
}

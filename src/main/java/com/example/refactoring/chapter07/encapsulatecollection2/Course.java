package com.example.refactoring.chapter07.encapsulatecollection2;

public class Course {

    private String name;
    private boolean isAdvanced;

    public Course(String name, boolean isAdvanced) {
        this.name = name;
        this.isAdvanced = isAdvanced;
    }

    public String getName() {
        return name;
    }

    public boolean isAdvanced() {
        return isAdvanced;
    }
}

package com.example.refactoring.chapter07.replaceprimitivewithobject3;

public class Priority {

    private String value;

    public Priority(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

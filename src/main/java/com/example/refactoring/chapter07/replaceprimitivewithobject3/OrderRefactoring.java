package com.example.refactoring.chapter07.replaceprimitivewithobject3;

public class OrderRefactoring {

    private Priority priority;

    public OrderRefactoring(String priority) {
        this.priority = new Priority(priority);
    }

    public String getPriorityString() {
        return this.priority.toString();
    }

    public void setPriority(String priority) {
        this.priority = new Priority(priority);
    }
}

package com.example.refactoring.chapter01.asis;

public class Play {
    private String name;
    private PlayType type;

    public Play(String name, PlayType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public PlayType getType() {
        return type;
    }
}
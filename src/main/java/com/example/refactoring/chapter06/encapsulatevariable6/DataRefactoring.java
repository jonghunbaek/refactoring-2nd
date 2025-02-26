package com.example.refactoring.chapter06.encapsulatevariable6;

import java.util.HashMap;
import java.util.Map;

public class DataRefactoring {

    private Map<String, String> defaultOwner;

    public DataRefactoring() {
        defaultOwner = new HashMap<>();
        defaultOwner.put("firstName", "마틴");
        defaultOwner.put("lastName", "파울러");
    }

    public void setDefaultOwner(Map<String, String> defaultOwner) {
        this.defaultOwner = defaultOwner;
    }

    public Map<String, String> getDefaultOwner() {
        return this.defaultOwner;
    }
}

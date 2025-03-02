package com.example.refactoring.chapter06.encapsulatevariable6;

import java.util.Map;

public class Owner {

    private Map<String, String> defaultOwner;

    public Owner(Map<String, String> defaultOwner) {
        this.defaultOwner = defaultOwner;
    }
}

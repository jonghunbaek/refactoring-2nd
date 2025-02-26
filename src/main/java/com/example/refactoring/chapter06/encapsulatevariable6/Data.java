package com.example.refactoring.chapter06.encapsulatevariable6;

import java.util.HashMap;
import java.util.Map;

public class Data {

    public static Map<String, String> defaultOwner = new HashMap<>();

    static {
        defaultOwner.put("firstName", "마틴");
        defaultOwner.put("lastName", "파울러");
    }
}

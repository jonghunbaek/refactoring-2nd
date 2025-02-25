package com.example.refactoring.chapter06.inlinefunction2;

import java.util.HashMap;
import java.util.Map;

public class Refactoring {

    public Map<String, String> reportLines(Customer customer) {
        Map<String, String> lines = new HashMap<String, String>();
        lines.put("name", customer.getName());
        lines.put("location", customer.getLocation());
        return lines;
    }
}

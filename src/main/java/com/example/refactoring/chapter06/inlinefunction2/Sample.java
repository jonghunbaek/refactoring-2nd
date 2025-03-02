package com.example.refactoring.chapter06.inlinefunction2;

import java.util.HashMap;
import java.util.Map;

public class Sample {

    public Map<String, String> reportLines(Customer customer) {
        Map<String, String> lines = new HashMap<String, String>();
        gatherCustomerData(lines, customer);
        return lines;
    }

    private void gatherCustomerData(Map<String, String> out, Customer customer) {
        out.put("name", customer.getName());
        out.put("location", customer.getLocation());
    }
}

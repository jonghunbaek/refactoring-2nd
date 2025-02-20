package com.example.refactoring.chapter04;

import java.util.List;

public class ProvinceData {

    private String name;
    private List<ProducerData> producers;
    private int totalProduction;
    private int demand;
    private int price;

    public String getName() {
        return name;
    }

    public List<ProducerData> getProducers() {
        return producers;
    }

    public int getTotalProduction() {
        return totalProduction;
    }

    public int getDemand() {
        return demand;
    }

    public int getPrice() {
        return price;
    }
}

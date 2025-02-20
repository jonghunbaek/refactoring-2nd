package com.example.refactoring.chapter04;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Province {

    private String name;
    private List<Producer> producers;
    private int totalProduction;
    private int demand;
    private int price;

    public Province(ProvinceData data) {
        this.name = data.getName();
        this.demand = data.getDemand();
        this.price = data.getPrice();
        this.producers = toProducers(data);
        this.totalProduction = sumProduction();
    }

    private List<Producer> toProducers(ProvinceData data) {
        return data.getProducers().stream()
                .map(producerData -> new Producer(this, producerData))
                .collect(Collectors.toList());
    }

    private int sumProduction() {
        return producers.stream()
                .mapToInt(Producer::getProduction)
                .sum();
    }

    public int getShortfall() {
        return this.demand - this.totalProduction;
    }

    public int getProfit() {
        return getDemandValue() - getDemandCost();
    }

    private int getDemandValue() {
        return getSatisfiedDemand() * this.price;
    }

    private int getSatisfiedDemand() {
        return Math.min(this.demand, this.totalProduction);
    }

    private int getDemandCost() {
        producers.sort(Comparator.comparing(Producer::getCost));

        int remainingDemand = this.demand;
        int result = 0;
        for (Producer producer : producers) {
            int contribution = Math.min(remainingDemand, producer.getProduction());
            remainingDemand -= contribution;
            result += contribution * producer.getCost();
        }

        return result;
    }

    public String getName() {
        return name;
    }

    public List<Producer> getProducers() {
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

    public void setTotalProduction(int totalProduction) {
        this.totalProduction = totalProduction;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

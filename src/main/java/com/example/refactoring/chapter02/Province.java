package com.example.refactoring.chapter02;

import java.util.List;

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
                .toList();
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
//        get demandCost() {
//            let remainigDemand = this.demand;
//            let result = 0;
//            this.producers
//                    .sort((a,b) => a.cost - b.cost)
//                    .forEach(p => {
//                        const contribution = Math.min(remainingDemand, p.production);
//                        remainingDemand -= contribution;
//                        result += contribution * p.cost;
//                    });
//            return result;
//        }

        return 0;
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

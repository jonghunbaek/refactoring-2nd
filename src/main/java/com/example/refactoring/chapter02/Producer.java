package com.example.refactoring.chapter02;

public class Producer {

    private Province province;
    private int cost;
    private String name;
    private int production;

    public Producer(Province province, ProducerData producerData) {
        this.province = province;
        this.cost = producerData.getCost();
        this.name = producerData.getName();
        this.production = producerData.getProduction();
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getProduction() {
        return production;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setProduction(String amountStr) {
        int newProduction = 0;
        if (!(amountStr == null || amountStr.isBlank())) {
            newProduction = Integer.parseInt(amountStr);
        }

        province.setTotalProduction(province.getTotalProduction() + (newProduction - this.production));
        this.production = newProduction;
    }
}

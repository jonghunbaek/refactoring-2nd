package com.example.refactoring.chapter10.specialCase5;

public class Customer {

    private String name;
    private String billingPlan;
    private String paymentHistory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBillingPlan() {
        return billingPlan;
    }

    public void setBillingPlan(String billingPlan) {
        this.billingPlan = billingPlan;
    }

    public String getPaymentHistory() {
        return paymentHistory;
    }

    public void setPaymentHistory(String paymentHistory) {
        this.paymentHistory = paymentHistory;
    }

    public boolean isUnknown() {
        return false;
    }
}

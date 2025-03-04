package com.example.refactoring.chapter07.inlineclass;

public class Shipment {

    private String shippingCompany;
    private String trackingNumber;

    public String getTrackingInfo() {
        return shippingCompany + ": " + trackingNumber;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public void setTrackingInformation(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }
}

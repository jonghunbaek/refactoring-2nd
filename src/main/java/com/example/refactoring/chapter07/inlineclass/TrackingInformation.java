package com.example.refactoring.chapter07.inlineclass;

public class TrackingInformation {

    private String shippingCompany;
    private String trackingNumber;

    public String getDisplay() {
        return shippingCompany + ": " + trackingNumber;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}

package com.example.refactoring.chapter12.subclassWithDelegate10;

public class PremiumBookingDelegate {

    private Booking hostBooking;
    private Extras extras;

    public PremiumBookingDelegate(Booking hostBooking, Extras extras) {
        this.hostBooking = hostBooking;
        this.extras = extras;
    }

    public boolean hasTalkback() {
        return hostBooking.show.hasOwnProperty("talkback");
    }

    public int extendBasePrice(int base) {
        return Math.round(base + this.extras.getPremiumFee());
    }

    public boolean hasDinner() {
        return this.extras.hasOwnProperty("dinner") && !this.hostBooking.isPeakDay;
    }
}

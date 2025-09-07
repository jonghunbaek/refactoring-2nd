package com.example.refactoring.chapter12.subclassWithDelegate10;

import java.time.LocalDate;

public class Booking {

    protected Show show;
    protected LocalDate date;
    protected boolean isPeakDay;
    protected PremiumBookingDelegate premiumDelegate;

    public Booking(Show show, LocalDate date) {
        this.show = show;
        this.date = date;
    }

    public static Booking createBooking(Show show, LocalDate date) {
        return new Booking(show, date);
    }

    public static Booking createPremiumBooking(Show show, LocalDate date, Extras extras) {
        Booking result = new Booking(show, date);
        result.bePremium(extras);
        return result;
    }

    protected void bePremium(Extras extras) {
        this.premiumDelegate = new PremiumBookingDelegate(this, extras);
    }

    public boolean hasTalkback() {
        return (this.premiumDelegate) != null ? this.premiumDelegate.hasTalkback() : this.show.hasOwnProperty("talkback") && !isPeakDay;
    }

    public int basePrice() {
        int result = this.show.getPrice();
        if (isPeakDay) result += (int) Math.round(result * 0.15);

        return this.premiumDelegate != null ? this.premiumDelegate.extendBasePrice(result) : result;
    }

    public boolean hasDinner() {
        return this.premiumDelegate != null ? this.premiumDelegate.hasDinner() : false;
    }
}

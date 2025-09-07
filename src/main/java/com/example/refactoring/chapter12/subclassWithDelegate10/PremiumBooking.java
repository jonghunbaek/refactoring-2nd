package com.example.refactoring.chapter12.subclassWithDelegate10;

import java.time.LocalDate;

public class PremiumBooking extends Booking {

    private Extras extras;

    public PremiumBooking(Show show, LocalDate date, Extras extras) {
        super(show, date);
        this.extras = extras;
    }

    @Override
    public int basePrice() {
        return Math.round(super.basePrice() + this.extras.getPremiumFee());
    }

}

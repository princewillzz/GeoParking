package com.geoparking.bookingservice.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Embeddable
@Data
public class BookingBillEmbeddable {

    @NotNull
    @Column(nullable = false)
    private Double totalAmount;
    private double discount;
    private double wallet;

    @NotNull
    @Column(nullable = false)
    private Double amountToPay;

    public BookingBillEmbeddable() {
        this.totalAmount = 0.0;
        this.discount = 0.0;
        this.wallet = 0.0;
        this.amountToPay = 0.0;
    }

}
package com.geoparking.bookingservice.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.geoparking.bookingservice.model.BookingBillEmbeddable;

import lombok.Data;

@Data
public class BookingDTO {

    private UUID uid;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    // Basic booking info
    private Date arrivalTimeDate;

    private Date departureTimeDate;

    private BookingBillEmbeddable bill;
    // private Double totalAmount;

    // private double wallet;

    // private double discount;

    // private Double amountToPay;
    // End of basic booking info

    private String parkingId;

    @JsonProperty(value = "order_id")
    private String razorpayOrderId;

    @JsonProperty(value = "payment_id")
    private String razorpayPaymentId;

    @JsonProperty(value = "signature")
    private String razorpaySignature;

    private boolean isPaymentDone;

}
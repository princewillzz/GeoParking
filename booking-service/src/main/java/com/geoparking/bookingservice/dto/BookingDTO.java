package com.geoparking.bookingservice.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.geoparking.bookingservice.model.BookingBillEmbeddable;
import com.geoparking.bookingservice.model.BookingStatus;

import lombok.Data;

@Data
public class BookingDTO {

    @JsonProperty(value = "id")
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

    private String customerId;

    @JsonProperty(value = "order_id", access = Access.WRITE_ONLY)
    private String razorpayOrderId;

    @JsonProperty(value = "payment_id", access = Access.WRITE_ONLY)
    private String razorpayPaymentId;

    @JsonProperty(value = "signature", access = Access.WRITE_ONLY)
    private String razorpaySignature;

    private boolean isPaymentDone;

    @JsonProperty(value = "status")
    private BookingStatus bookingStatus;

}
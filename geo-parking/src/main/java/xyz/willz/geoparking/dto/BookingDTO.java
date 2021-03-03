package xyz.willz.geoparking.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BookingDTO {

    private UUID uid;

    private String createdAt;

    private String updatedAt;

    private String fromTimeDate;

    private String toTimeDate;

    private Double totalAmount;

    private Double wallet;

    private Double discount;

    @JsonProperty(value = "order_id")
    private String razorpayOrderId;

    @JsonProperty(value = "payment_id")
    private String razorpayPaymentId;
    
    @JsonProperty(value = "signature")
    private String razorpaySignature;

    private boolean isPaymentDone;



}

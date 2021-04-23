package com.geoparking.bookingservice.util;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = false)
public class RazorpayCallbackOnSuccessRequest {

    @NotNull
    @NotBlank
    @JsonProperty("razorpay_payment_id")
    private String razorpayPaymentId;

    @NotNull
    @NotBlank
    @JsonProperty("razorpay_order_id")
    private String razorpayOrderId;

    @NotNull
    @NotBlank
    @JsonProperty("razorpay_signature")
    private String razorpaySignature;

}

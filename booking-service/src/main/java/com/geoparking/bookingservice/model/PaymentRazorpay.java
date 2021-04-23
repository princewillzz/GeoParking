package com.geoparking.bookingservice.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRazorpay {

    String id;
    String order_id;
    String email;
    String contact;

    String description;
    Date created_at;

    String status;
    Boolean captured;
    String method;

    String fee;
    String tax;
    Float amount;

    String error_code;
    String error_description;
    String error_step;
    String error_source;
    String error_reason;

    String card_id;
    String bank;
    String vpa;

}
package com.geoparking.bookingservice.model;

public enum BookingStatus {

    PAYMENT_PENDING(Code.PAYMENT_PENDING), PENDING(Code.PENDING), CANCELLED(Code.CANCELLED);

    public final String code;

    BookingStatus(String code) {
        this.code = code;
    }

    public class Code {
        public static final String PAYMENT_PENDING = "PAYMENT_PENDING";
        public static final String PENDING = "PENDING";
        public static final String CANCELLED = "CANCELLED";
    }

}
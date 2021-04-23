package com.geoparking.bookingservice.model;

public enum BookingStatus {

    PENDING(Code.PENDING), CANCELLED(Code.CANCELLED);

    public final String code;

    BookingStatus(String code) {
        this.code = code;
    }

    public class Code {
        public static final String PENDING = "PENDING";
        public static final String CANCELLED = "CANCELLED";
    }

}
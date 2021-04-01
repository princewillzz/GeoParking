package com.geoparking.parkingservice.Exception;

public class InvalidParkingAddressException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message = "Invalid Address";

    public InvalidParkingAddressException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

package com.geoparking.bookingservice.util;

import lombok.Data;

@Data
public class CheckAvailabilityForm {

    private String parkingId;

    private String arrivalDate;
    private String arrivalTime;

    private String departureDate;
    private String departureTime;

}

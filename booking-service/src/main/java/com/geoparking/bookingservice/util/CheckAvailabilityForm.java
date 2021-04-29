package com.geoparking.bookingservice.util;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CheckAvailabilityForm {

    @NotNull
    @NotBlank
    private String parkingId;

    @NotNull
    @NotBlank
    private String arrivalDate;
    private String arrivalTime;

    @NotNull
    @NotBlank
    private String departureDate;
    private String departureTime;

}

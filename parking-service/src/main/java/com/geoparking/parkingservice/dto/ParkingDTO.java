package com.geoparking.parkingservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingDTO {

    private String id;

    private String name;
    private String address;
    private String hourlyRent;

}

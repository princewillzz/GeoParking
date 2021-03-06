package com.geoparking.parkingservice.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ParkingDTO implements Serializable {

    static final long serialVersionUID = 1L;

    private String id;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be blank")
    private String name;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be blank")
    @Size(min = 5, max = 300)
    private String address;

    @NotNull(message = "cannot be null")
    private Double hourlyRent;

    private boolean active;

    private long timeBooked;

    private int totalSlots;

    private String ownerId;

    private ParkingCoordinate position;

}

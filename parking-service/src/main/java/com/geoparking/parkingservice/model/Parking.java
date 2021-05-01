package com.geoparking.parkingservice.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("parking")
@Data
public class Parking {

    @Id
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

    @GeoSpatialIndexed
    GeoJsonPoint location;

    @NotNull
    @NotBlank
    private String ownerId;

}

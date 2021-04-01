package com.geoparking.parkingservice.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("parking")
@Data
public class Parking {

    @Id
    private String id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 10)
    private String address;

    @NotNull
    private String hourlyRent;

}

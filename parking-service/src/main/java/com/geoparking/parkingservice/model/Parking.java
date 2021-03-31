package com.geoparking.parkingservice.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("parking")
@Data
public class Parking {

    @Id
    private String id;
    private String name;
    private String address;
    private String hourlyRent;


}

package xyz.willz.geoparking.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ParkingDTO {

    private UUID uid;

    private String name;

    private String latitude;

    private String longitude;

    private String address;

    private Boolean isActive;

    private Integer total;

    private Integer occupied;

    private Integer vacant;

    private long noOfTimesBooked;

}

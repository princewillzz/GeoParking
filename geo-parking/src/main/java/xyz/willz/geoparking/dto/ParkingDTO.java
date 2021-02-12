package xyz.willz.geoparking.dto;

import lombok.Data;

@Data
public class ParkingDTO {

    private String uid;

    private String latitude;

    private String longitude;

    private String address;

    private Boolean isActive;

}

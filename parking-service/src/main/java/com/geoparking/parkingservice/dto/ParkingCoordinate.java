package com.geoparking.parkingservice.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingCoordinate implements Serializable {

    private double latitude;
    private double longitude;

}

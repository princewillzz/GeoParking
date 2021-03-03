package xyz.willz.geoparking.utilities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingAvailabilityForm {

    String parkingId;
    String arrivalDate;
    String arrivalTime;
    String departureDate;
    String departureTime;

}

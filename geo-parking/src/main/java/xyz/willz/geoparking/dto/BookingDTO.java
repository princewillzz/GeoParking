package xyz.willz.geoparking.dto;

import lombok.Data;

@Data
public class BookingDTO {

    private String uid;

    private String createdAt;

    private String updatedAt;

    private String fromTimeDate;

    private String toTimeDate;

    private Double totalAmount;

    private Double wallet;

    private Double discount;

}

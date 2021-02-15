package xyz.willz.geoparking.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class BookingDTO {

    private UUID uid;

    private String createdAt;

    private String updatedAt;

    private String fromTimeDate;

    private String toTimeDate;

    private Double totalAmount;

    private Double wallet;

    private Double discount;

}

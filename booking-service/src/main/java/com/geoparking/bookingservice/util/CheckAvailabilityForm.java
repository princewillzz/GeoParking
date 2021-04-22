package com.geoparking.bookingservice.util;

import java.util.Date;

import lombok.Data;

@Data
public class CheckAvailabilityForm {

    private Date fromDate;
    private Date fromTime;

    private String toDate;
    private String toTime;

}

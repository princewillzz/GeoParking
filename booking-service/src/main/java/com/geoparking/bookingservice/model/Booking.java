package com.geoparking.bookingservice.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "booking")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true, value = { "customer", "parking" })
public class Booking {

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "uid", nullable = false, updatable = false, unique = true)
    private UUID uid;

    @Column(unique = true)
    private String bookingKey;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private Date deletedAt;

    @Column(nullable = false)
    private Date fromTimeDate;

    @Column(nullable = false)
    private Date toTimeDate;

    @Embedded
    private BookingBillEmbeddable bill;

    private String customerid;

    private String parkingId;

    @Column(unique = true, nullable = true)
    private String razorpayOrderId;

    @Column(unique = true, nullable = true)
    private String razorpayPaymentId;

    private String razorpaySignature;

    private boolean isPaymentDone;

    private BookingStatus bookingStatus;

}
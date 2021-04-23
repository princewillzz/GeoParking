package com.geoparking.bookingservice.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "booking")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
    private Date arrivalTimeDate;

    @Column(nullable = false)
    private Date departureTimeDate;

    @Embedded
    private BookingBillEmbeddable bill;

    private String customerId;

    private String parkingId;

    @JsonProperty(value = "order_id")
    @Column(unique = true, nullable = true)
    private String razorpayOrderId;

    @JsonProperty(value = "payment_id")
    @Column(unique = true, nullable = true)
    private String razorpayPaymentId;

    @JsonProperty(value = "signature")
    private String razorpaySignature;

    private boolean isPaymentDone;

    private BookingStatus bookingStatus;

    @PrePersist
    void prePersist() {
        if (this.createdAt == null)
            this.createdAt = new Date();
    }

}
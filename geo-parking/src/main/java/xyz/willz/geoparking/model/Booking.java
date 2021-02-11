package xyz.willz.geoparking.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "booking")
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue
    private UUID uid;

    private Date createdAt;

    private Date updatedAt;

    @Column(nullable = false)
    private Date fromTimeDate;

    @Column(nullable = false)
    private Date toTimeDate;

    private BookingBillEmbeddable bill;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Parking parking;

    @PrePersist
    void prePersist() {
        this.createdAt = new Date();
        this.updatedAt = createdAt;
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = new Date();
    }

}

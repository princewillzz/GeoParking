package xyz.willz.geoparking.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parking", uniqueConstraints = { @UniqueConstraint(columnNames = { "latitude", "longitude" }) })
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Parking {

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "uid", unique = true, nullable = false, updatable = false)
    private UUID uid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false, columnDefinition = "boolean DEFAULT true")
    private Boolean isActive;

    @Column(nullable = false)
    private Integer total;

    @Column(nullable = false)
    private Integer occupied;

    @Column(nullable = false)
    private Integer vacant;

    @Column(nullable = false)
    private Double hourlyRate;

    private long noOfTimesBooked;

    @ManyToOne(fetch = FetchType.LAZY)
    private Renter renter;

    @PrePersist
    void prePersist() {

        if (this.isActive == null) {
            this.isActive = true;
        }

    }

}

package com.zippy.stations.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
/**
 * Clase para el manejo de estaciones
 *
 * @author Johan Jerez
 * @since 1.0
 *
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "station")
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String stationName;

    @Column(name = "address")
    private String stationAddress;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "station_status_id")
    private Long stationStatusId;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "last_manteinance")
    private Date lastManteinanceDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_status_id", insertable = false, updatable = false)
    private StationState stationState;

}

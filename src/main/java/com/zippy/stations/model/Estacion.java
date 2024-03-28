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
public class Estacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String nombreEstacion;

    @Column(name = "address")
    private String direccionEstacion;

    @Column(name = "latitude")
    private Double latitud;

    @Column(name = "longitude")
    private Double longitud;

    @Column(name = "station_status_id")
    private Long idEstadoEstacion;

    @Column(name = "capacity")
    private Integer capacidad;

    @Column(name = "last_manteinance")
    private Date FechaultimoMantenimiento;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_status_id", insertable = false, updatable = false)
    private EstadoEstacion estadoEstacion;

}

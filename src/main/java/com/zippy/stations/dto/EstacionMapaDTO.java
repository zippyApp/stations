package com.zippy.stations.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para exclusivamnete renderizar las estaciones en el mapa
 *
 * @author Johan Jerez
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class EstacionMapaDTO {

    private Long id;
    private String nombreEstacion;
    private Double latitud;
    private Double longitud;

}

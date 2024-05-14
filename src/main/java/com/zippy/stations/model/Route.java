package com.zippy.stations.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@NoArgsConstructor
@Table(name = "routes")
public class Route {
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "origin_station_id")
    private Long originStationId;

    @Column(name = "destination_station_id")
    private Long destinationStationId;

    @Column(name="duration")
    private Integer duration;

    @Column(name="distance")
    private Double distance;

    @Column(name = "direction_response", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode directionResponse;

}

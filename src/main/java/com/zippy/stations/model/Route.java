package com.zippy.stations.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "routes")
public class Route implements Serializable {
    @Serial
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

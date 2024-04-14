package com.zippy.stations.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "station_status")
public class StationStatus implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String stationName;

    @JsonIgnore
    @OneToMany(mappedBy = "stationStatus")
    private List<Station> station;

}

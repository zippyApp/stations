package com.zippy.stations.dto;

import com.zippy.stations.model.StationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class StationDTO {

    private Long id;
    private String stationName;
    private String stationAddress;
    private Double latitude;
    private Double longitude;
    private StationStatus stationStatus;
    private Integer capacity;
    private Date lastMaintenance;

}

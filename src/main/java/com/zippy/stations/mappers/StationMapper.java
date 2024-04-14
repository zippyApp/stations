package com.zippy.stations.mappers;

import com.zippy.stations.dto.StationDTO;
import com.zippy.stations.model.Station;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper de estaciones
 *
 * @author Johan Jerez
 *
 */
@Mapper(componentModel = "spring")
public interface StationMapper {

    @Mapping(source="stationStatus.id", target="stationStatus.id")
    @Mapping(source= "stationStatus.stationName", target="stationStatus.stationName")
    StationDTO toStationDTO(Station station);

}

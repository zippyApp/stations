package com.zippy.stations.mappers;

import com.zippy.stations.dto.StationDTO;
import com.zippy.stations.model.Station;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StationMapper {
    StationDTO toStationDTO(Station station);
}

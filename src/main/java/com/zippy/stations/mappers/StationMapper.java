package com.zippy.stations.mappers;
import com.zippy.stations.dto.StationMapDTO;
import com.zippy.stations.model.Station;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper de estaciones
 *
 * @author Johan Jerez
 *
 */
@Mapper
public interface StationMapper {
    StationMapper INSTANCE = Mappers.getMapper(StationMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "stationName", target = "name")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    StationMapDTO toStationMapDTO(Station station);

}

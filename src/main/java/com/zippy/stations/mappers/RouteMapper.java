package com.zippy.stations.mappers;


import com.zippy.stations.dto.RouteDTO;
import com.zippy.stations.model.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    @Mapping(source="originStationId", target="originStationId")
    @Mapping(source="destinationStationId", target="destinationStationId")
    @Mapping(source="duration", target="duration")
    @Mapping(source="distance", target="distance")
    RouteDTO toRouteDTO(Route route);

}

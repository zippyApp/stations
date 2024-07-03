package com.zippy.stations.mappers;

import com.zippy.stations.dto.RouteDTO;
import com.zippy.stations.model.Route;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    RouteDTO toRouteDTO(Route route);
}

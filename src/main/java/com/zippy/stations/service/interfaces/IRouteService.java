package com.zippy.stations.service.interfaces;

import com.fasterxml.jackson.databind.JsonNode;
import com.zippy.stations.model.Route;
import java.util.Optional;

public interface IRouteService {
    Optional<JsonNode> getRouteUserToOrigin(String userCoordinates, Long originStationId);

    Optional<JsonNode> getRouteOriginToDestination(Long originStationId, Long destinationStationId);

    Optional<Route> getRouteInformation(Long originStationId, Long destinationStationId);

}

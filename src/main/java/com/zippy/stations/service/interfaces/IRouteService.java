package com.zippy.stations.service.interfaces;

import com.fasterxml.jackson.databind.JsonNode;
import com.zippy.stations.model.Route;
import org.springframework.stereotype.Service;

@Service
public interface IRouteService {

    JsonNode getRouteUserToOrigin(String userCoordinates, Long originStationId);

    JsonNode getRouteOriginToDestination(Long originStationId, Long destinationStationId);

    Route getRouteInformation(Long originStationId, Long destinationStationId);

}

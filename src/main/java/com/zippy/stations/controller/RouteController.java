package com.zippy.stations.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.zippy.stations.dto.RouteDTO;
import com.zippy.stations.mappers.RouteMapper;
import com.zippy.stations.service.interfaces.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/route")
public class RouteController {
    private IRouteService routeService;
    private RouteMapper routeMapper;

    @GetMapping("/user-to-origin/{userCoordinates}/{originStationId}")
    public ResponseEntity<JsonNode> getUserToOrigin(@PathVariable String userCoordinates, @PathVariable Long originStationId) {
        return routeService.getRouteUserToOrigin(userCoordinates, originStationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/origin-to-destination/{originStationId}/{destinationStationId}")
    public ResponseEntity<JsonNode> getOriginToDestination(@PathVariable Long originStationId, @PathVariable Long destinationStationId) {
        return routeService.getRouteOriginToDestination(originStationId, destinationStationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/info/{originStationId}/{destinationStationId}")
    public ResponseEntity<RouteDTO> getRouteInfo(@PathVariable Long originStationId, @PathVariable Long destinationStationId) {
        return routeService.getRouteInformation(originStationId, destinationStationId)
                .map(routeMapper::toRouteDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Autowired
    public void setRouteService(IRouteService routeService) {
        this.routeService = routeService;
    }

    @Autowired
    public void setRouteMapper(RouteMapper routeMapper) {
        this.routeMapper = routeMapper;
    }
}

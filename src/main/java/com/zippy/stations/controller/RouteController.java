package com.zippy.stations.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.zippy.stations.dto.RouteDTO;
import com.zippy.stations.mappers.RouteMapper;
import com.zippy.stations.model.Route;
import com.zippy.stations.service.interfaces.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("toOrigin/{userCoordinates}/{originStationId}")
    public ResponseEntity<JsonNode> getRouteUserToOrigin(@PathVariable String userCoordinates, @PathVariable Long originStationId) {
        JsonNode directionResponse = routeService.getRouteUserToOrigin(userCoordinates, originStationId);
        if(directionResponse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(directionResponse, HttpStatus.OK);
    }

    @GetMapping("toDestination/{originStationId}/{destinationStationId}")
    public ResponseEntity<JsonNode> getRouteOriginToDestination(@PathVariable Long originStationId, @PathVariable Long destinationStationId) {
        JsonNode directionResponse = routeService.getRouteOriginToDestination(originStationId, destinationStationId);
        if(directionResponse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(directionResponse, HttpStatus.OK);
    }
    
    @GetMapping("information/{originStationId}/{destinationStationId}")
    public ResponseEntity<RouteDTO> getRouteInformation(@PathVariable Long originStationId, @PathVariable Long destinationStationId) {
        Route route = routeService.getRouteInformation(originStationId, destinationStationId);
        if(route == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(routeMapper.toRouteDTO(route), HttpStatus.OK);
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

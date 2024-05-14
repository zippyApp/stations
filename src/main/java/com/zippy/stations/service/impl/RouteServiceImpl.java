package com.zippy.stations.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.zippy.stations.clients.MapboxClient;
import com.zippy.stations.model.Route;
import com.zippy.stations.model.Station;
import com.zippy.stations.repository.RouteRepository;
import com.zippy.stations.service.interfaces.IRouteService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class RouteServiceImpl implements IRouteService {
    private StationServiceImpl stationService;
    private MapboxClient mapboxClient;
    private RouteRepository routeRepository;

    @PostConstruct
    @Scheduled(cron = "0 0 4,12,18 * * *")
    @Transactional
    public void setAllRoutes() {

        List<Station> stations = stationService.getAllStations();
        List<Route> routes = new ArrayList<>();

        for (Station origin : stations) {
            for (Station destination : stations) {

                if (!origin.equals(destination)) {

                    //Miramos si hay una ruta registrada entre las dos estaciones en la base de datos, si es así se actuliza, si no se crea uno nuevo
                    Route route = routeRepository.findByOriginStationIdAndDestinationStationId(
                            origin.getId(), destination.getId());
                    if(route == null) {
                        route = new Route();
                    }
                    //Llamamos la api de mapbox para obtener la ruta entre las dos estaciones
                    String coordinates = String.format(Locale.US, "%.6f,%.6f;%.6f,%.6f",
                            origin.getLongitude(), origin.getLatitude(),
                            destination.getLongitude(), destination.getLatitude());
                    JsonNode directionResponse = mapboxClient.getRoutetoDestination(coordinates);

                    //Extraemos la información de la respuesta de la api relevante y la guardamos en la base de datos
                    route.setOriginStationId(origin.getId());
                    route.setDestinationStationId(destination.getId());
                    route.setDistance( Math.round(directionResponse.get("routes").get(0).get("distance").asDouble() / 10.0) / 100.0);
                    route.setDuration((int) Math.floor(directionResponse.get("routes").get(0).get("duration").asDouble() / 60.0));

                    route.setDirectionResponse(directionResponse);
                    routeRepository.save(route);

                    routes.add(route);
                }
            }
        }
        log.info("Rutas actualizadas");
    }

    @Override
    @Transactional
    public JsonNode getRouteUserToOrigin(String userCoordinates, Long originStationId) {
        Station origin = stationService.findStationById(originStationId);
        if(origin == null) {
            return null;
        }
        String coordinates = String.format(Locale.US, "%s;%.6f,%.6f", userCoordinates, origin.getLongitude(), origin.getLatitude());
        return mapboxClient.getRoutetoOrigin(coordinates);
    }

    @Override
    @Transactional
    public JsonNode getRouteOriginToDestination(Long originStationId, Long destinationStationId) {
        Station origin = stationService.findStationById(originStationId);
        Station destination = stationService.findStationById(destinationStationId);
        if(origin == null || destination == null) {
            return null;
        }
        Route route = routeRepository.findByOriginStationIdAndDestinationStationId(originStationId, destinationStationId);

        return route.getDirectionResponse();

    }

    @Override
    @Transactional
    public Route getRouteInformation(Long originStationId, Long destinationStationId) {
        return routeRepository.findByOriginStationIdAndDestinationStationId(originStationId, destinationStationId);
    }

    @Autowired
    public void setStationServiceImp(StationServiceImpl stationService) {
        this.stationService = stationService;
    }

    @Autowired
    public void setMapBox(MapboxClient mapboxClient) {
        this.mapboxClient = mapboxClient;
    }

    @Autowired
    public void setRouteRepository(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

}

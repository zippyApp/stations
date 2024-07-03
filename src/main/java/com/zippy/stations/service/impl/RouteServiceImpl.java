package com.zippy.stations.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.zippy.stations.clients.MapboxClient;
import com.zippy.stations.model.Route;
import com.zippy.stations.model.Station;
import com.zippy.stations.repository.IRouteRepository;
import com.zippy.stations.service.interfaces.IRouteService;
import com.zippy.stations.service.interfaces.IStationService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Locale;
import java.util.Optional;

@Service
public class RouteServiceImpl implements IRouteService {
    private static final Logger log = LoggerFactory.getLogger(RouteServiceImpl.class);
    private IStationService stationService;
    private MapboxClient mapboxClient;
    private IRouteRepository routeRepository;

    @PostConstruct
    @Scheduled(cron = "0 0 4,12,18 * * *")
    @Transactional
    public void setAllRoutes() {
        stationService.getAllStations()
                .stream()
                .flatMap(origin -> stationService.getAllStations().stream()
                        .filter(destination -> !origin.equals(destination))
                        .map(destination -> updateOrCreateRoute(origin, destination)))
                .forEach(routeRepository::save);
        log.info("All routes have been updated");
    }

    @Override
    @Transactional
    public Optional<JsonNode> getRouteUserToOrigin(String userCoordinates, Long originStationId) {
        return stationService.findStationById(originStationId)
                .map(station -> formatUserToStationCoordinates(userCoordinates, station))
                .flatMap(mapboxClient::getRouteToOrigin);
    }

    @Override
    @Transactional
    public Optional<JsonNode> getRouteOriginToDestination(Long originStationId, Long destinationStationId) {
        return getSavedRoute(originStationId, destinationStationId)
                .map(Route::getDirectionResponse);
    }

    @Override
    @Transactional
    public Optional<Route> getRouteInformation(Long originStationId, Long destinationStationId) {
        return getSavedRoute(originStationId, destinationStationId);
    }

    private Route updateOrCreateRoute(Station origin, Station destination) {
        Optional<JsonNode> directionResponse = getRouteFromMapbox(origin, destination);
        return getSavedRoute(origin.getId(), destination.getId())
                .map(route -> updateRoute(route, directionResponse.orElse(null)))
                .orElseGet(() -> createRoute(origin, destination, directionResponse.orElse(null)));
    }

    private Route updateRoute(Route existingRoute, JsonNode directionResponse) {
        if (directionResponse != null) {
            existingRoute.setDistance(calculateDistance(directionResponse));
            existingRoute.setDuration(calculateDuration(directionResponse));
            existingRoute.setDirectionResponse(directionResponse);
        }
        return existingRoute;
    }

    private Route createRoute(Station origin, Station destination, JsonNode directionResponse) {
        return Route.builder()
                .originStationId(origin.getId())
                .destinationStationId(destination.getId())
                .distance(calculateDistance(directionResponse))
                .duration(calculateDuration(directionResponse))
                .directionResponse(directionResponse)
                .build();
    }

    private Optional<JsonNode> getRouteFromMapbox(Station origin, Station destination) {
        return mapboxClient.getRouteToDestination(formatCoordinates(origin, destination));
    }

    private String formatCoordinates(Station origin, Station destination) {
        return String.format(Locale.US, "%.6f,%.6f;%.6f,%.6f",
                origin.getLongitude(), origin.getLatitude(),
                destination.getLongitude(), destination.getLatitude());
    }

    private String formatUserToStationCoordinates(String userCoordinates, Station station) {
        return String.format(Locale.US, "%s;%.6f,%.6f", userCoordinates, station.getLongitude(), station.getLatitude());
    }

    private double calculateDistance(JsonNode directions) {
        return Math.round(directions.get("routes").get(0).get("distance").asDouble() / 10.0) / 100.0;
    }

    private int calculateDuration(JsonNode directions) {
        return (int) Math.floor(directions.get("routes").get(0).get("duration").asDouble() / 60.0);
    }

    private Optional<Route> getSavedRoute(Long originStationId, Long destinationStationId) {
        return routeRepository.findByOriginStationIdAndDestinationStationId(
                originStationId, destinationStationId);
    }

    @Autowired
    public void setStationService(IStationService stationService) {
        this.stationService = stationService;
    }

    @Autowired
    public void setMapboxClient(MapboxClient mapboxClient) {
        this.mapboxClient = mapboxClient;
    }

    @Autowired
    public void setRouteRepository(IRouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }
}

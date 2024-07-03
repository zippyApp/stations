package com.zippy.stations.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.zippy.stations.configuration.MapboxClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

@FeignClient(value = "stationsFeign", url = "https://api.mapbox.com/directions/v5/mapbox", configuration = MapboxClientConfig.class)
public interface MapboxClient {

    @GetMapping(value = "/{typeOfVehicle}/{coordinates}", produces = "application/json")
    Optional<JsonNode> getRoute(
            @PathVariable("typeOfVehicle") String typeOfVehicle,
            @PathVariable("coordinates") String coordinates,
            @RequestParam("geometries") String geometries,
            @RequestParam("alternatives") String alternatives,
            @RequestParam("language") String language,
            @RequestParam("overview") String overview,
            @RequestParam("steps") String steps
    );

    default Optional<JsonNode> getRouteToOrigin(String coordinates) {
        return getRoute(
                "walking",
                coordinates,
                "geojson",
                "false",
                "es",
                "full",
                "false"
        );
    }

    default Optional<JsonNode> getRouteToDestination(String coordinates) {
        return getRoute(
                "cycling",
                coordinates,
                "geojson",
                "false",
                "es",
                "full",
                "false"
        );
    }

}

package com.zippy.stations.clients;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "stationsFeign", url = "https://api.mapbox.com/directions/v5/mapbox")
public interface MapboxClient {


    @GetMapping(value = "/{typeOfVehicle}/{coordinates}", produces = "application/json")
    JsonNode getRoute(@PathVariable(name = "typeOfVehicle") String typeOfVehicle,
                             @PathVariable(name = "coordinates") String coordinates,
                             @RequestParam(name = "access_token") String accessToken,
                             @RequestParam("geometries") String geometries,
                             @RequestParam("alternatives") String alternatives,
                             @RequestParam("language") String language,
                             @RequestParam("overview") String overview,
                             @RequestParam("steps") String steps);

    default JsonNode getRoutetoOrigin(String coordinates) {
        String accessToken = "pk.eyJ1Ijoic2Vic3RpaWFuIiwiYSI6ImNsdTl5eGV0NzBlaGsycXBubDlrd2xoZjIifQ.3cUWTjnCSnAmVUW--A9NDA";
        return getRoute(
                "walking",
                coordinates,
                accessToken,
                "geojson",
                "false",
                "es",
                "full",
                "false");
    }

    default JsonNode getRoutetoDestination(String coordinates) {
        String accessToken = "pk.eyJ1Ijoic2Vic3RpaWFuIiwiYSI6ImNsdTl5eGV0NzBlaGsycXBubDlrd2xoZjIifQ.3cUWTjnCSnAmVUW--A9NDA";
        return getRoute(
                "cycling",
                coordinates,
                accessToken,
                "geojson",
                "false",
                "es",
                "full",
                "false");
    }

}

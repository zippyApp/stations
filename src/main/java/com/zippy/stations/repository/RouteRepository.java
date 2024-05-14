package com.zippy.stations.repository;

import com.zippy.stations.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {

    Route findByOriginStationIdAndDestinationStationId(Long originStationId, Long destinationStationId);
}

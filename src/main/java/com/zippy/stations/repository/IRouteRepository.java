package com.zippy.stations.repository;

import com.zippy.stations.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IRouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByOriginStationIdAndDestinationStationId(Long originStationId, Long destinationStationId);
}

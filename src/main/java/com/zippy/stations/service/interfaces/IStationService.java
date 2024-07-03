package com.zippy.stations.service.interfaces;

import com.zippy.stations.model.Station;
import java.util.List;
import java.util.Optional;

public interface IStationService {
    List<Station> getAllStations();

    Optional<Station> findStationById(Long idStation);

    Optional<Station> updateStationStatus(Long idStation, Long idStatus);

    Optional<Station> updateStationCapacity(Long stationId, Integer capacity);
}

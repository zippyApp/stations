package com.zippy.stations.service.interfaces;

import com.zippy.stations.model.Station;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface de servicio de estaciones
 *
 * @author Johan Jerez
 * @since 1.0
 */
@Service
public interface IStationService {

    List<Station> getAllStations();
    Station findStationById(Long idStation);

    Station updateStationStatus(Long idStation, Long idStatus);

    Station updateStationCapacity(Long stationId, Integer capacity);
}

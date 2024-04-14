package com.zippy.stations.service.impl;

import com.zippy.stations.model.Station;
import com.zippy.stations.repository.IEstacionesRepository;
import com.zippy.stations.service.interfaces.IStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de estaciones
 *
 * @author Jerson Johan Jerez Vargas
 * @since 1.0
 */
@Service
public class StationServiceImpl implements IStationService {

    private IEstacionesRepository estacionesRepository;

    public List<Station> getAllStations() {
        return estacionesRepository.findAll();
    }
    public Station findStationById(Long idStation) {
        return estacionesRepository.findById(idStation).orElse(null);
    }

    public Station updateStationStatus(Long idStation, Long idStatus) {
        Station station = findStationById(idStation);
        if(station == null){
            return null;
        }
        station.setStationStatusId(idStatus);
        return estacionesRepository.save(station);
    }

    public Station updateStationCapacity(Long stationId, Integer capacity) {
        Station station = findStationById(stationId);
        if(station == null){
            return null;
        }
        station.setCapacity(capacity);
        return estacionesRepository.save(station);
    }
    @Autowired
    public void setestacionesRepository(IEstacionesRepository estacionesRepository) {
        this.estacionesRepository = estacionesRepository;
    }
}

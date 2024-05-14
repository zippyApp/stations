package com.zippy.stations.service.impl;

import com.zippy.stations.model.Station;
import com.zippy.stations.repository.IStationRepository;
import com.zippy.stations.service.interfaces.IStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de estaciones
 *
 * @author Jerson Johan Jerez Vargas
 * @since 1.0
 */
@Service
public class StationServiceImpl implements IStationService {

    private IStationRepository stationRepository;

    @Override
    @Transactional
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    @Override
    @Transactional
    public Station findStationById(Long idStation) {
        return stationRepository.findById(idStation).orElse(null);
    }

    @Override
    @Transactional
    public Station updateStationStatus(Long idStation, Long idStatus) {
        Station station = findStationById(idStation);
        if(station == null){
            return null;
        }
        station.setStationStatusId(idStatus);
        return stationRepository.save(station);
    }

    @Override
    @Transactional
    public Station updateStationCapacity(Long stationId, Integer capacity) {
        Station station = findStationById(stationId);
        if(station == null){
            return null;
        }
        station.setCapacity(capacity);
        return stationRepository.save(station);
    }

    @Autowired
    public void setestacionesRepository(IStationRepository estacionesRepository) {
        this.stationRepository = estacionesRepository;
    }
}

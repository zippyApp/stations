package com.zippy.stations.service.impl;

import com.zippy.stations.model.Station;
import com.zippy.stations.repository.IStationRepository;
import com.zippy.stations.repository.IStationStatusRepository;
import com.zippy.stations.service.interfaces.IStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class StationServiceImpl implements IStationService {

    private IStationRepository stationRepository;
    private IStationStatusRepository stationStatusRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Station> findStationById(Long idStation) {
        return stationRepository.findById(idStation);
    }

    @Override
    @Transactional
    public Optional<Station> updateStationStatus(Long idStation, Long idStatus) {
        return stationStatusExists(idStatus)
                .filter(Boolean::booleanValue)
                .flatMap(exists -> stationRepository.findById(idStation)
                        .map(station -> station.setStationStatusId(idStatus))
                        .map(this::save)
                );
    }

    @Override
    @Transactional
    public Optional<Station> updateStationCapacity(Long stationId, Integer capacity) {
        return stationRepository.findById(stationId)
                .map(station -> station.setCapacity(capacity))
                .map(stationRepository::save);
    }

    private Station save(Station station) {
        Station updatedStation = stationRepository.saveAndFlush(station);
        entityManager.flush();
        entityManager.refresh(updatedStation);
        return updatedStation;
    }

    private Optional<Boolean> stationStatusExists(Long statusId) {
        return Optional.of(stationStatusRepository.existsById(statusId));
    }


    @Autowired
    public void setStationRepository(IStationRepository estacionesRepository) {
        this.stationRepository = estacionesRepository;
    }

    @Autowired
    public void setStationStatusRepository(IStationStatusRepository stationStatusRepository) {
        this.stationStatusRepository = stationStatusRepository;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}

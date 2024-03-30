package com.zippy.stations.service.impl;

import com.zippy.stations.dto.StationMapDTO;
import com.zippy.stations.mappers.StationMapper;
import com.zippy.stations.model.Station;
import com.zippy.stations.repository.IStationRepository;
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

    private IStationRepository stationsRepository;
    public List<StationMapDTO> getOpenStationsMap() {

        List<Station> openStations = stationsRepository.findAllOpenStations();
        return openStations.stream().map(StationMapper.INSTANCE::toStationMapDTO).toList();

    }

    @Autowired
    public void setStationsRepository(IStationRepository stationsRepository) {
        this.stationsRepository = stationsRepository;
    }
}

package com.zippy.stations.controller;

import com.zippy.stations.dto.StationDTO;
import com.zippy.stations.mappers.StationMapper;
import com.zippy.stations.model.Station;
import com.zippy.stations.repository.IStationStatusRepository;
import com.zippy.stations.service.interfaces.IStationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


/**
 * Clase controladora para estaciones
 *
 * @author Jerson Johan Jerez Vargas
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/stations")
public class StationController {

    private IStationService stationService;
    private StationMapper stationMapper;
    private IStationStatusRepository stationStatusRepository;

    /**
     * Metodo para obtener todas las estaciones con stationStatus (id and statusName)
     */
    @GetMapping("/all")
    public ResponseEntity<List<StationDTO>> getStationsMap() {

        List<Station> stations = stationService.getAllStations();
        if(stations == null || stations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(stations.stream().map(station->stationMapper.toStationDTO(station)).toList(), HttpStatus.OK);
        }
    }

    /**
     * Endpoint para obtener una estación por su id
     * @param stationId
     */
    @GetMapping("/getStationById/{stationId}")
    public ResponseEntity<StationDTO> getStationById(@PathVariable Long stationId) {
        Station station = stationService.findStationById(stationId);
        if(station == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(stationMapper.toStationDTO(station), HttpStatus.OK);
        }
    }

    /**
     * Endpoint para actualizar el estado de la estación
     * @param stationId
     * @param stationStatusId
     */
    @PutMapping("/updateStationStatus")
    public ResponseEntity<StationDTO> updateStationStatus(@RequestHeader Long stationId, @RequestHeader Long stationStatusId) {

        //Si el id del status proporcionado por el usuario no existe, se retorna un BAD_REQUEST
        if (!stationStatusRepository.existsById(stationStatusId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Station station = stationService.updateStationStatus(stationId, stationStatusId);
        if(station == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(stationMapper.toStationDTO(station), HttpStatus.OK);
        }
    }

    @PutMapping("/updateStationCapacity")
    public ResponseEntity<StationDTO> updateStationCapacity(@RequestHeader Long stationId, @RequestHeader Integer capacity) {
        Station station = stationService.updateStationCapacity(stationId, capacity);
        if(station == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(stationMapper.toStationDTO(station), HttpStatus.OK);
        }
    }

    @Autowired
    public void setEstacionService(IStationService stationService) {
        this.stationService = stationService;
    }

    @Autowired
    public void setStationMapper(StationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }

    @Autowired
    public void setStationStatusRepository(IStationStatusRepository stationStatusRepository) {
        this.stationStatusRepository = stationStatusRepository;
    };

}

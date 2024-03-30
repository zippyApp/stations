package com.zippy.stations.controller;

import com.zippy.stations.dto.StationMapDTO;
import com.zippy.stations.service.interfaces.IStationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Clase controladora para estaciones
 *
 * @author Jerson Johan Jerez Vargas
 * @since 1.0
 */
@RestController
@RequestMapping("/api/stations")
public class StationController {

    private IStationService stationService;

    @GetMapping("/getOpenStationsMap")
    public ResponseEntity<List<StationMapDTO>> getOpenStationsMap() {

        List<StationMapDTO> estacionesAbiertasMapa = stationService.getOpenStationsMap();

        if(estacionesAbiertasMapa == null || estacionesAbiertasMapa.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(estacionesAbiertasMapa, HttpStatus.OK);
        }
    }

    @Autowired
    public void setStationService(IStationService stationService) {
        this.stationService = stationService;
    }

}

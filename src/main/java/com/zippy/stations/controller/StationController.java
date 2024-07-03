package com.zippy.stations.controller;

import com.zippy.stations.dto.StationDTO;
import com.zippy.stations.mappers.StationMapper;
import com.zippy.stations.service.interfaces.IStationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
public class StationController {

    private IStationService stationService;
    private StationMapper stationMapper;

    @GetMapping("/all")
    public ResponseEntity<List<StationDTO>> getStationsMap() {
        return ResponseEntity.ok(stationService.getAllStations().stream().map(stationMapper::toStationDTO).toList());
    }

    @GetMapping("/{stationId}")
    public ResponseEntity<StationDTO> getStationById(@PathVariable Long stationId) {
        return stationService.findStationById(stationId)
                .map(stationMapper::toStationDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/update/status")
    public ResponseEntity<StationDTO> updateStationStatus(@RequestHeader Long stationId, @RequestHeader Long stationStatusId) {
        return stationService.updateStationStatus(stationId, stationStatusId)
                .map(stationMapper::toStationDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/update/capacity")
    public ResponseEntity<StationDTO> updateStationCapacity(@RequestHeader Long stationId, @RequestHeader Integer capacity) {
        return stationService.updateStationCapacity(stationId, capacity)
                .map(stationMapper::toStationDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @Autowired
    public void setStationService(IStationService stationService) {
        this.stationService = stationService;
    }

    @Autowired
    public void setStationMapper(StationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }
}

package com.zippy.stations.controller;

import com.zippy.stations.dto.EstacionMapaDTO;
import com.zippy.stations.service.interfaces.IEstacionService;
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
@RequestMapping("/api/estaciones")
public class EstacionController {

    private IEstacionService estacionService;

    @GetMapping("/getEstacionesAbiertasMapa")
    public ResponseEntity<List<EstacionMapaDTO>> getEstacionesAbiertasMapa() {

        List<EstacionMapaDTO> estacionesAbiertasMapa = estacionService.getEstacionesAbiertasMapa();

        if(estacionesAbiertasMapa == null || estacionesAbiertasMapa.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(estacionesAbiertasMapa, HttpStatus.OK);
        }
    }

    @Autowired
    public void setEstacionService(IEstacionService estacionService) {
        this.estacionService = estacionService;
    }

}

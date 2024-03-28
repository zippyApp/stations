package com.zippy.stations.service.impl;

import com.zippy.stations.dto.EstacionMapaDTO;
import com.zippy.stations.mappers.EstacionMapper;
import com.zippy.stations.model.Estacion;
import com.zippy.stations.repository.IEstacionesRepository;
import com.zippy.stations.service.interfaces.IEstacionService;
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
public class EstacionServiceImpl implements IEstacionService {

    private IEstacionesRepository estacionesRepository;
    public List<EstacionMapaDTO> getEstacionesAbiertasMapa() {

        List<Estacion> estacionesAbiertas = estacionesRepository.findAllEstacionesAbiertas();
        return estacionesAbiertas.stream().map(EstacionMapper.INSTANCE::toEstacionMapaDTO).toList();

    }

    @Autowired
    public void setestacionesRepository(IEstacionesRepository estacionesRepository) {
        this.estacionesRepository = estacionesRepository;
    }
}

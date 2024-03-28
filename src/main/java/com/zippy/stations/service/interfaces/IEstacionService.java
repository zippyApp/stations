package com.zippy.stations.service.interfaces;

import com.zippy.stations.dto.EstacionMapaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface de servicio de estaciones
 *
 * @author Johan Jerez
 * @since 1.0
 */
@Service
public interface IEstacionService {

    public List<EstacionMapaDTO> getEstacionesAbiertasMapa();

}

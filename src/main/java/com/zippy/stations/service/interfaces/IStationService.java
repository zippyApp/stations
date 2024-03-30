package com.zippy.stations.service.interfaces;

import com.zippy.stations.dto.StationMapDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface de servicio de estaciones
 *
 * @author Johan Jerez
 * @since 1.0
 */
@Service
public interface IStationService {

    public List<StationMapDTO> getOpenStationsMap();

}

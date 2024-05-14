package com.zippy.stations.repository;

import com.zippy.stations.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de ESTACIONES
 *
 * @author Johan Jerez
 * @since 1.0
 */
@Repository
public interface IStationRepository extends JpaRepository<Station, Long> {

}

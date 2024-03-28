package com.zippy.stations.repository;

import com.zippy.stations.model.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de ESTACIONES
 *
 * @author Johan Jerez
 * @since 1.0
 */
@Repository
public interface IEstacionesRepository extends JpaRepository<Estacion, Long> {

    List<Estacion> findByIdEstadoEstacion(Long idEstadoEstacion);

    default List<Estacion> findAllEstacionesAbiertas() {
        return findByIdEstadoEstacion(1L);
    }

}

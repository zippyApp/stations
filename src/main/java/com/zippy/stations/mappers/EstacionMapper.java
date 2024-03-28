package com.zippy.stations.mappers;
import com.zippy.stations.dto.EstacionMapaDTO;
import com.zippy.stations.model.Estacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper de estaciones
 *
 * @author Johan Jerez
 *
 */
@Mapper
public interface EstacionMapper {
    EstacionMapper INSTANCE = Mappers.getMapper(EstacionMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombreEstacion", target = "nombreEstacion")
    @Mapping(source = "latitud", target = "latitud")
    @Mapping(source = "longitud", target = "longitud")
    EstacionMapaDTO toEstacionMapaDTO(Estacion estacion);

}

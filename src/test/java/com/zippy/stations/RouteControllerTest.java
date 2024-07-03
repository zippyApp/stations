package com.zippy.stations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zippy.stations.controller.RouteController;
import com.zippy.stations.dto.RouteDTO;
import com.zippy.stations.mappers.RouteMapper;
import com.zippy.stations.model.Route;
import com.zippy.stations.service.interfaces.IRouteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RouteController.class)
public class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRouteService routeService;

    @MockBean
    private RouteMapper routeMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Caso de prueba para el método getUserToOrigin.
     * Simula un escenario exitoso donde se encuentra la ruta desde las coordenadas del usuario hasta la estación de origen.
     * Se espera el estado HTTP 200 (OK) y una respuesta JSON con los detalles de la ruta.
     */
    @Test
    public void testGetUserToOrigin_Success() throws Exception {
        String userCoordinates = "40.712776,-74.005974";
        Long originStationId = 1L;
        JsonNode jsonNode = objectMapper.createObjectNode();

        when(routeService.getRouteUserToOrigin(anyString(), anyLong())).thenReturn(Optional.ofNullable(jsonNode));

        assert jsonNode != null;
        mockMvc.perform(get("/api/v1/route/toOrigin/{userCoordinates}/{originStationId}", userCoordinates, originStationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNode.toString()));
    }

    /**
     * Caso de prueba para el método getUserToOrigin.
     * Simula un escenario donde no se encuentra la ruta desde las coordenadas del usuario hasta la estación de origen.
     * Se espera el estado HTTP 404 (Not Found).
     */
    @Test
    public void testGetUserToOrigin_NotFound() throws Exception {
        String userCoordinates = "40.712776,-74.005974";
        Long originStationId = 1L;

        when(routeService.getRouteUserToOrigin(anyString(), anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/v1/route/toOrigin/{userCoordinates}/{originStationId}", userCoordinates, originStationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Caso de prueba para el método getOriginToDestination.
     * Simula un escenario exitoso donde se encuentra la ruta desde la estación de origen hasta la estación de destino.
     * Se espera el estado HTTP 200 (OK) y una respuesta JSON con los detalles de la ruta.
     */
    @Test
    public void testGetOriginToDestination_Success() throws Exception {
        Long originStationId = 1L;
        Long destinationStationId = 2L;
        JsonNode jsonNode = objectMapper.createObjectNode();

        when(routeService.getRouteOriginToDestination(anyLong(), anyLong())).thenReturn(Optional.ofNullable(jsonNode));

        assert jsonNode != null;
        mockMvc.perform(get("/api/v1/route/toDestination/{originStationId}/{destinationStationId}", originStationId, destinationStationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNode.toString()));
    }

    /**
     * Caso de prueba para el método getOriginToDestination.
     * Simula un escenario donde no se encuentra la ruta desde la estación de origen hasta la estación de destino.
     * Se espera el estado HTTP 404 (Not Found).
     */
    @Test
    public void testGetOriginToDestination_NotFound() throws Exception {
        Long originStationId = 1L;
        Long destinationStationId = 2L;

        when(routeService.getRouteOriginToDestination(anyLong(), anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/v1/route/toDestination/{originStationId}/{destinationStationId}", originStationId, destinationStationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Caso de prueba para el método getRouteInfo.
     * Simula un escenario exitoso donde se encuentra la información de la ruta entre la estación de origen y la estación de destino.
     * Se espera el estado HTTP 200 (OK) y una respuesta JSON con los detalles de la ruta.
     */
    @Test
    public void testGetRouteInfo_Success() throws Exception {
        Long originStationId = 1L;
        Long destinationStationId = 2L;
        Route route = new Route();
        RouteDTO routeDTO = new RouteDTO();

        when(routeService.getRouteInformation(anyLong(), anyLong())).thenReturn(Optional.of(route));
        when(routeMapper.toRouteDTO(Mockito.any(Route.class))).thenReturn(routeDTO);

        mockMvc.perform(get("/api/v1/route/information/{originStationId}/{destinationStationId}", originStationId, destinationStationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(routeDTO)));
    }

    /**
     * Caso de prueba para el método getRouteInfo.
     * Simula un escenario donde no se encuentra la información de la ruta entre la estación de origen y la estación de destino.
     * Se espera el estado HTTP 404 (Not Found).
     */
    @Test
    public void testGetRouteInfo_NotFound() throws Exception {
        Long originStationId = 1L;
        Long destinationStationId = 2L;

        when(routeService.getRouteInformation(anyLong(), anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/v1/route/information/{originStationId}/{destinationStationId}", originStationId, destinationStationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}



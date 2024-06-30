package com.zippy.stations;

import com.zippy.stations.controller.StationController;
import com.zippy.stations.dto.StationDTO;
import com.zippy.stations.mappers.StationMapper;
import com.zippy.stations.model.Station;
import com.zippy.stations.repository.IStationStatusRepository;
import com.zippy.stations.service.interfaces.IStationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(StationController.class)
@ActiveProfiles("test") // Activa el perfil de prueba
public class StationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStationService stationService;

    @MockBean
    private StationMapper stationMapper;

    @MockBean
    private IStationStatusRepository stationStatusRepository;

    /**
     * Prueba que verifica que la solicitud para obtener todas las estaciones responda con un estado 200 OK
     * y el tipo de contenido correcto cuando hay estaciones disponibles.
     */
    @Test
    void testGetStationsMap_validRequest() throws Exception {
        List<Station> stations = new ArrayList<>();
        Station station = new Station();
        stations.add(station);
        StationDTO stationDTO = new StationDTO();

        Mockito.when(stationService.getAllStations()).thenReturn(stations);
        Mockito.when(stationMapper.toStationDTO(station)).thenReturn(stationDTO);

        mockMvc.perform(get("/api/v1/stations/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Prueba que verifica que la solicitud para obtener todas las estaciones responda con un estado 204 No Content
     * cuando no hay estaciones disponibles.
     */
    @Test
    void testGetStationsMap_noContent() throws Exception {
        Mockito.when(stationService.getAllStations()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/stations/all"))
                .andExpect(status().isNoContent());
    }

    /**
     * Prueba que verifica que la solicitud para obtener una estación por su ID responda con un estado 200 OK
     * y el tipo de contenido correcto cuando la estación existe.
     */
    @Test
    void testGetStationById_validRequest() throws Exception {
        Station station = new Station();
        StationDTO stationDTO = new StationDTO();

        Mockito.when(stationService.findStationById(anyLong())).thenReturn(station);
        Mockito.when(stationMapper.toStationDTO(station)).thenReturn(stationDTO);

        mockMvc.perform(get("/api/v1/stations/getStationById/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Prueba que verifica que la solicitud para obtener una estación por su ID responda con un estado 404 Not Found
     * cuando la estación no existe.
     */
    @Test
    void testGetStationById_notFound() throws Exception {
        Mockito.when(stationService.findStationById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/v1/stations/getStationById/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Prueba que verifica que la solicitud para actualizar el estado de una estación responda con un estado 200 OK
     * y el tipo de contenido correcto cuando el estado y la estación existen.
     */
    @Test
    void testUpdateStationStatus_validRequest() throws Exception {
        Station station = new Station();
        StationDTO stationDTO = new StationDTO();

        Mockito.when(stationStatusRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(stationService.updateStationStatus(anyLong(), anyLong())).thenReturn(station);
        Mockito.when(stationMapper.toStationDTO(station)).thenReturn(stationDTO);

        mockMvc.perform(put("/api/v1/stations/updateStationStatus")
                        .header("stationId", 1L)
                        .header("stationStatusId", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Prueba que verifica que la solicitud para actualizar el estado de una estación responda con un estado 400 Bad Request
     * cuando el ID del estado proporcionado no existe.
     */
    @Test
    void testUpdateStationStatus_badRequest() throws Exception {
        Mockito.when(stationStatusRepository.existsById(anyLong())).thenReturn(false);

        mockMvc.perform(put("/api/v1/stations/updateStationStatus")
                        .header("stationId", 1L)
                        .header("stationStatusId", 1L))
                .andExpect(status().isBadRequest());
    }

    /**
     * Prueba que verifica que la solicitud para actualizar el estado de una estación responda con un estado 404 Not Found
     * cuando la estación no existe.
     */
    @Test
    void testUpdateStationStatus_notFound() throws Exception {
        Mockito.when(stationStatusRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(stationService.updateStationStatus(anyLong(), anyLong())).thenReturn(null);

        mockMvc.perform(put("/api/v1/stations/updateStationStatus")
                        .header("stationId", 1L)
                        .header("stationStatusId", 1L))
                .andExpect(status().isNotFound());
    }

    /**
     * Prueba que verifica que la solicitud para actualizar la capacidad de una estación responda con un estado 200 OK
     * y el tipo de contenido correcto cuando la estación existe.
     */
    @Test
    void testUpdateStationCapacity_validRequest() throws Exception {
        Station station = new Station();
        StationDTO stationDTO = new StationDTO();

        Mockito.when(stationService.updateStationCapacity(anyLong(), anyInt())).thenReturn(station);
        Mockito.when(stationMapper.toStationDTO(station)).thenReturn(stationDTO);

        mockMvc.perform(put("/api/v1/stations/updateStationCapacity")
                        .header("stationId", 1L)
                        .header("capacity", 20))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Prueba que verifica que la solicitud para actualizar la capacidad de una estación responda con un estado 404 Not Found
     * cuando la estación no existe.
     */
    @Test
    void testUpdateStationCapacity_notFound() throws Exception {
        Mockito.when(stationService.updateStationCapacity(anyLong(), anyInt())).thenReturn(null);

        mockMvc.perform(put("/api/v1/stations/updateStationCapacity")
                        .header("stationId", 1L)
                        .header("capacity", 20))
                .andExpect(status().isNotFound());
    }
}

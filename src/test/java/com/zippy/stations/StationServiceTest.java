package com.zippy.stations;

import com.zippy.stations.model.Station;
import com.zippy.stations.repository.IStationRepository;
import com.zippy.stations.service.impl.StationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class StationServiceTest {

    @InjectMocks
    private StationServiceImpl stationService;

    @Mock
    private IStationRepository stationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba que verifica que el método getAllStations devuelva todas las estaciones disponibles.
     */
    @Test
    void testGetAllStations() {
        List<Station> stations = new ArrayList<>();
        stations.add(new Station());
        when(stationRepository.findAll()).thenReturn(stations);

        List<Station> result = stationService.getAllStations();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(stationRepository, times(1)).findAll();
    }

    /**
     * Prueba que verifica que el método findStationById devuelva la estación correcta cuando existe.
     */
    @Test
    void testFindStationById_found() {
        Station station = new Station();
        when(stationRepository.findById(anyLong())).thenReturn(Optional.of(station));

        Station result = stationService.findStationById(1L);
        assertNotNull(result);
        verify(stationRepository, times(1)).findById(1L);
    }

    /**
     * Prueba que verifica que el método findStationById devuelva null cuando la estación no existe.
     */
    @Test
    void testFindStationById_notFound() {
        when(stationRepository.findById(anyLong())).thenReturn(Optional.empty());

        Station result = stationService.findStationById(1L);
        assertNull(result);
        verify(stationRepository, times(1)).findById(1L);
    }

    /**
     * Prueba que verifica que el método updateStationStatus actualice y devuelva la estación correcta cuando existe.
     */
    @Test
    void testUpdateStationStatus_success() {
        Station station = new Station();
        when(stationRepository.findById(anyLong())).thenReturn(Optional.of(station));
        when(stationRepository.save(any(Station.class))).thenReturn(station);

        Station result = stationService.updateStationStatus(1L, 2L);
        assertNotNull(result);
        assertEquals(2L, station.getStationStatusId());
        verify(stationRepository, times(1)).findById(1L);
        verify(stationRepository, times(1)).save(station);
    }

    /**
     * Prueba que verifica que el método updateStationStatus devuelva null cuando la estación no existe.
     */
    @Test
    void testUpdateStationStatus_notFound() {
        when(stationRepository.findById(anyLong())).thenReturn(Optional.empty());

        Station result = stationService.updateStationStatus(1L, 2L);
        assertNull(result);
        verify(stationRepository, times(1)).findById(1L);
        verify(stationRepository, never()).save(any(Station.class));
    }

    /**
     * Prueba que verifica que el método updateStationCapacity actualice y devuelva la estación correcta cuando existe.
     */
    @Test
    void testUpdateStationCapacity_success() {
        Station station = new Station();
        when(stationRepository.findById(anyLong())).thenReturn(Optional.of(station));
        when(stationRepository.save(any(Station.class))).thenReturn(station);

        Station result = stationService.updateStationCapacity(1L, 30);
        assertNotNull(result);
        assertEquals(30, station.getCapacity());
        verify(stationRepository, times(1)).findById(1L);
        verify(stationRepository, times(1)).save(station);
    }

    /**
     * Prueba que verifica que el método updateStationCapacity devuelva null cuando la estación no existe.
     */
    @Test
    void testUpdateStationCapacity_notFound() {
        when(stationRepository.findById(anyLong())).thenReturn(Optional.empty());

        Station result = stationService.updateStationCapacity(1L, 30);
        assertNull(result);
        verify(stationRepository, times(1)).findById(1L);
        verify(stationRepository, never()).save(any(Station.class));
    }
}

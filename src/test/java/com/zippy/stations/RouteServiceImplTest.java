package com.zippy.stations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zippy.stations.clients.MapboxClient;
import com.zippy.stations.model.Route;
import com.zippy.stations.model.Station;
import com.zippy.stations.repository.RouteRepository;
import com.zippy.stations.service.impl.RouteServiceImpl;
import com.zippy.stations.service.impl.StationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RouteServiceImplTest {

    @Mock
    private StationServiceImpl stationService;

    @Mock
    private MapboxClient mapboxClient;

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteServiceImpl routeService;

    @Captor
    private ArgumentCaptor<Route> routeCaptor;

    private Station station1;
    private Station station2;
    private Route route;
    private JsonNode mapboxResponse;

    @BeforeEach
    void setUp() throws Exception {
        station1 = new Station();
        station1.setId(1L);
        station1.setLatitude(7.140709);
        station1.setLongitude(-73.121012);

        station2 = new Station();
        station2.setId(2L);
        station2.setLatitude(7.116833);
        station2.setLongitude(-73.105517);

        route = new Route();
        route.setOriginStationId(1L);
        route.setDestinationStationId(2L);

        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = "{ \"routes\": [{ \"distance\": 4498.1, \"duration\": 1264.6 }] }";
        mapboxResponse = mapper.readTree(jsonResponse);
    }

    /**
     * Prueba para el método setAllRoutes.
     * Propósito: Verificar que el método setAllRoutes actualiza y guarda correctamente las rutas en el repositorio.
     */
    @Test
    void setAllRoutes_shouldUpdateRoutes() {
        // Configurar las dependencias simuladas
        when(stationService.getAllStations()).thenReturn(Arrays.asList(station1, station2));
        when(mapboxClient.getRoutetoDestination(anyString())).thenReturn(mapboxResponse);
        when(routeRepository.findByOriginStationIdAndDestinationStationId(anyLong(), anyLong())).thenReturn(null);

        // Ejecutar el método setAllRoutes
        routeService.setAllRoutes();

        // Verificar que el repositorio de rutas haya guardado las rutas calculadas
        verify(routeRepository, times(2)).save(routeCaptor.capture());
        List<Route> capturedRoutes = routeCaptor.getAllValues();

        // Validar las rutas capturadas
        assertEquals(2, capturedRoutes.size());
        assertEquals(1L, capturedRoutes.get(0).getOriginStationId());
        assertEquals(2L, capturedRoutes.get(0).getDestinationStationId());
        assertEquals(4.5, capturedRoutes.get(0).getDistance());
        assertEquals(21, capturedRoutes.get(0).getDuration());
    }

    /**
     * Prueba para el método getRouteUserToOrigin.
     * Propósito: Verificar que el método getRouteUserToOrigin devuelve una ruta válida cuando se proporcionan coordenadas de usuario y una estación válida.
     */
    @Test
    void getRouteUserToOrigin_shouldReturnRoute() {
        when(stationService.findStationById(1L)).thenReturn(station1);
        when(mapboxClient.getRoutetoOrigin(anyString())).thenReturn(mapboxResponse);

        JsonNode result = routeService.getRouteUserToOrigin("7.140709,-73.121012", 1L);

        assertNotNull(result);
        assertEquals(4498.1, result.get("routes").get(0).get("distance").asDouble());
    }

    /**
     * Prueba para el método getRouteUserToOrigin con una estación inválida.
     * Propósito: Verificar que el método getRouteUserToOrigin devuelve null cuando se proporciona una estación inválida.
     */
    @Test
    void getRouteUserToOrigin_shouldReturnNullForInvalidStation() {
        when(stationService.findStationById(1L)).thenReturn(null);

        JsonNode result = routeService.getRouteUserToOrigin("7.140709,-73.121012", 1L);

        assertNull(result);
    }

    /**
     * Prueba para el método getRouteOriginToDestination.
     * Propósito: Verificar que el método getRouteOriginToDestination devuelve una ruta válida entre dos estaciones válidas.
     */
    @Test
    void getRouteOriginToDestination_shouldReturnRoute() {
        when(stationService.findStationById(1L)).thenReturn(station1);
        when(stationService.findStationById(2L)).thenReturn(station2);
        when(routeRepository.findByOriginStationIdAndDestinationStationId(1L, 2L)).thenReturn(route);
        route.setDirectionResponse(mapboxResponse);

        JsonNode result = routeService.getRouteOriginToDestination(1L, 2L);

        assertNotNull(result);
        assertEquals(4498.1, result.get("routes").get(0).get("distance").asDouble());
    }

    /**
     * Prueba para el método getRouteOriginToDestination con estaciones inválidas.
     * Propósito: Verificar que el método getRouteOriginToDestination devuelve null cuando se proporcionan estaciones inválidas.
     */
    @Test
    void getRouteOriginToDestination_shouldReturnNullForInvalidStations() {
        when(stationService.findStationById(1L)).thenReturn(null);

        JsonNode result = routeService.getRouteOriginToDestination(1L, 2L);

        assertNull(result);
    }

    /**
     * Prueba para el método getRouteInformation.
     * Propósito: Verificar que el método getRouteInformation devuelve una ruta válida entre dos estaciones específicas.
     */
    @Test
    void getRouteInformation_shouldReturnRoute() {
        when(routeRepository.findByOriginStationIdAndDestinationStationId(1L, 2L)).thenReturn(route);

        Route result = routeService.getRouteInformation(1L, 2L);

        assertNotNull(result);
        assertEquals(1L, result.getOriginStationId());
        assertEquals(2L, result.getDestinationStationId());
    }
}


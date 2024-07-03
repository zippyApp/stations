package com.zippy.stations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zippy.stations.clients.MapboxClient;
import com.zippy.stations.model.Route;
import com.zippy.stations.model.Station;
import com.zippy.stations.repository.IRouteRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RouteServiceImplTest {

    @Mock
    private StationServiceImpl stationService;

    @Mock
    private MapboxClient mapboxClient;

    @Mock
    private IRouteRepository routeRepository;

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

    @Test
    void setAllRoutes_shouldUpdateRoutes() {
        // Assuming getAllStations returns List directly (no change needed if it's not wrapped in Optional)
        when(stationService.getAllStations()).thenReturn(Arrays.asList(station1, station2));
        when(mapboxClient.getRouteToDestination(anyString())).thenReturn(Optional.of(mapboxResponse));
        when(routeRepository.findByOriginStationIdAndDestinationStationId(anyLong(), anyLong())).thenReturn(Optional.empty());

        // Execute
        routeService.setAllRoutes();

        // Verify and assertions
        verify(routeRepository, times(2)).save(routeCaptor.capture());
        List<Route> capturedRoutes = routeCaptor.getAllValues();
        assertEquals(2, capturedRoutes.size());
    }

    @Test
    void getRouteUserToOrigin_shouldReturnRoute() {
        when(stationService.findStationById(1L)).thenReturn(Optional.of(station1));
        when(mapboxClient.getRouteToOrigin(anyString())).thenReturn(Optional.ofNullable(mapboxResponse));

        JsonNode result = routeService.getRouteUserToOrigin("7.140709,-73.121012", 1L).get();

        assertNotNull(result);
        assertEquals(4498.1, result.get("routes").get(0).get("distance").asDouble());
    }

    @Test
    void getRouteUserToOrigin_shouldReturnNullForInvalidStation() {
        when(stationService.findStationById(1L)).thenReturn(Optional.empty());

        JsonNode result = routeService.getRouteUserToOrigin("7.140709,-73.121012", 1L).get();

        assertNull(result);
    }

    @Test
    void getRouteOriginToDestination_shouldReturnRoute() {
        when(stationService.findStationById(1L)).thenReturn(Optional.of(station1));
        when(stationService.findStationById(2L)).thenReturn(Optional.of(station2));
        when(routeRepository.findByOriginStationIdAndDestinationStationId(1L, 2L)).thenReturn(Optional.of(route));

        JsonNode result = routeService.getRouteOriginToDestination(1L, 2L).get();

        assertNotNull(result);
        assertEquals(4498.1, result.get("routes").get(0).get("distance").asDouble());
    }

    @Test
    void getRouteOriginToDestination_shouldReturnNullForInvalidStations() {
        when(stationService.findStationById(1L)).thenReturn(Optional.empty());

        JsonNode result = routeService.getRouteOriginToDestination(1L, 2L).get();

        assertNull(result);
    }

    @Test
    void getRouteInformation_shouldReturnRoute() {
        when(routeRepository.findByOriginStationIdAndDestinationStationId(1L, 2L)).thenReturn(Optional.of(route));

        Route result = routeService.getRouteInformation(1L, 2L).get();

        assertNotNull(result);
        assertEquals(1L, result.getOriginStationId());
        assertEquals(2L, result.getDestinationStationId());
    }

}


package ru.fastdelivery.domain.common.coordinates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoordinatesValidationServiceTest {

    private CoordinatesConfigProvider coordinatesConfigProvider;
    private CoordinatesValidationService coordinatesValidationService;

    @BeforeEach
    void setUp() {
        coordinatesConfigProvider = mock(CoordinatesConfigProvider.class);
        coordinatesValidationService = new CoordinatesValidationService(coordinatesConfigProvider);
    }

    @Test
    void testValidateDeparture_ValidCoordinates_ReturnsDeparturePoint() {
        when(coordinatesConfigProvider.validLatitude(any())).thenReturn(true);
        when(coordinatesConfigProvider.validLongitude(any())).thenReturn(true);

        DeparturePoint departurePoint = coordinatesValidationService.validateDeparture(BigDecimal.valueOf(55.0), BigDecimal.valueOf(73.0));

        assertNotNull(departurePoint);
    }

    @Test
    void testValidateDeparture_InvalidLatitude_ThrowsIllegalArgumentException() {
        when(coordinatesConfigProvider.validLatitude(any())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            coordinatesValidationService.validateDeparture(BigDecimal.valueOf(10.0), BigDecimal.valueOf(73.0));
        });
    }

    @Test
    void testValidateDeparture_InvalidLongitude_ThrowsIllegalArgumentException() {

        when(coordinatesConfigProvider.validLatitude(any())).thenReturn(true);
        when(coordinatesConfigProvider.validLongitude(any())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            coordinatesValidationService.validateDeparture(BigDecimal.valueOf(55.0), BigDecimal.valueOf(200.0));
        });
    }

}

package ru.fastdelivery.domain.delivery.distance;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.coordinates.DeparturePoint;
import ru.fastdelivery.domain.common.coordinates.DestinationPoint;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistanceCalculatorTest {

    @Test
    void testCalculateDistance() {
        DeparturePoint departure = new DeparturePoint(BigDecimal.valueOf(77.1539), BigDecimal.valueOf(-120.398));
        DestinationPoint destination = new DestinationPoint(BigDecimal.valueOf(77.1804), BigDecimal.valueOf(129.55));

        DistanceCalculator distanceCalculator = new DistanceCalculator(departure, destination);

        double expectedDistance = 2332669;

        double actualDistance = distanceCalculator.calculateDistance();

        double delta = 0.5;
        assertEquals(expectedDistance, actualDistance, delta);
    }

}

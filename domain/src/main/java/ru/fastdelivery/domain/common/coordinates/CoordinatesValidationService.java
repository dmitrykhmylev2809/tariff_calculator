package ru.fastdelivery.domain.common.coordinates;

import lombok.RequiredArgsConstructor;


import java.math.BigDecimal;

public class CoordinatesValidationService {

    private final CoordinatesConfigProvider coordinatesConfigProvider;

    public CoordinatesValidationService(CoordinatesConfigProvider coordinatesConfigProvider) {
        this.coordinatesConfigProvider = coordinatesConfigProvider;
    }

    public DeparturePoint validateDeparture(BigDecimal latitude, BigDecimal longitude) {
        if (!coordinatesConfigProvider.validLatitude(latitude)) {
            throw new IllegalArgumentException("Departure invalid latitude");
        }
        if (!coordinatesConfigProvider.validLongitude(longitude)) {
            throw new IllegalArgumentException("Departure invalid longitude");
        }

        return new DeparturePoint(latitude, longitude);
    }

    public DestinationPoint validateDestination(BigDecimal latitude, BigDecimal longitude) {
        if (!coordinatesConfigProvider.validLatitude(latitude)) {
            throw new IllegalArgumentException("Destination invalid latitude");
        }
        if (!coordinatesConfigProvider.validLongitude(longitude)) {
            throw new IllegalArgumentException("Destination invalid longitude");
        }

        return new DestinationPoint(latitude, longitude);
    }


}

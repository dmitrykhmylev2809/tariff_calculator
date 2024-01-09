package ru.fastdelivery.properties.provider;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.coordinates.CoordinatesConfigProvider;

import java.math.BigDecimal;

/**
 * Настройки координат из конфига
 */
@Configuration
@ConfigurationProperties("coordinates")
@Setter
public class CoordinatesProperties implements CoordinatesConfigProvider {

    private BigDecimal minLatitude;
    private BigDecimal maxLatitude;
    private BigDecimal minLongitude;
    private BigDecimal maxLongitude;


    @Override
    public boolean validLatitude(BigDecimal latitude) {
        return latitude.compareTo(minLatitude) >= 0 && latitude.compareTo(maxLatitude) <= 0;
    }

    @Override
    public boolean validLongitude(BigDecimal longitude) {
        return longitude.compareTo(minLongitude) >= 0 && longitude.compareTo(maxLongitude) <= 0;
    }
}
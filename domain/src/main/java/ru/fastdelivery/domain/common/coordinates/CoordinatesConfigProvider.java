package ru.fastdelivery.domain.common.coordinates;

import java.math.BigDecimal;

/**
 * Проверка доступности использования координат
 */
public interface CoordinatesConfigProvider {

    /**
     * @param latitude,longitude трехбуквенный код валюты
     * @return доступно ли использование координат в приложении
     */
    boolean validLatitude(BigDecimal latitude);
    boolean validLongitude(BigDecimal longitude);

}


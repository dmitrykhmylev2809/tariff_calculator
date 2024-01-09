package ru.fastdelivery.domain.delivery.pack;

import java.math.BigDecimal;

/**
 * Упаковка груза
 *
 * @param cargoVolume объем упаковки
 */
public record PackVolume(BigDecimal cargoVolume) {
}

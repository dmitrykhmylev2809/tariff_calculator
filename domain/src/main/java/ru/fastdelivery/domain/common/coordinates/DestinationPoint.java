package ru.fastdelivery.domain.common.coordinates;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public record DestinationPoint(
        @Schema(description = "Широта точки отправки", example = "55.446008")
        BigDecimal latitude,

        @Schema(description = "Долгота точки отправки", example = "65.339151")
        BigDecimal longitude
) {
}

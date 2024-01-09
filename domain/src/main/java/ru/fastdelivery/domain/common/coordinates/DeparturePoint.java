package ru.fastdelivery.domain.common.coordinates;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


public record DeparturePoint(
        @Schema(description = "Широта точки доставки", example = "73.398660")
        BigDecimal latitude,

        @Schema(description = "Долгота точки доставки", example = "55.027532")
        BigDecimal longitude


        )
{

}